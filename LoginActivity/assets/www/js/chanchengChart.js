var gov=new Object();
var Class = {
	create: function() {
	    return function() {
	    	this.initialize.apply(this, arguments);
	    }
  	}
}
Object.extend = function(destination, source) {
  for (var property in source) {
    destination[property] = source[property];
  }
  return destination;
}
var $_ = function(elem) {
	if (arguments.length > 1) {
		for (var i = 0, elems = [], length = arguments.length; i < length; i++)
			elems.push($_(arguments[i]));
		return elems;
	}
    if (typeof elem == 'string') {
      return document.getElementById(elem);
    } else {
      return elem;
    }
 };
var period =  Class.create();
period.prototype = {
			initialize:function(value,time){
				this.value = value;			   
				this.time = time;
			}
};
gov.Graphic = Class.create();
gov.Graphic.prototype={
	initialize: function(data,elm,options){
		this.setOptions(options);
		this.entity=document.createElement("div");
		this.pointBox=$_(elm);
		this.showPointGraphic(data);
	},
	setOptions: function(options) {
		this.options = {
			height:170,                 //绘图区域高度
			maxHeight:50,              //y轴最高数值
			barDistance:26,           //x轴坐标间距
			topDistance:0,             //上部填充
			bottomDistance:0,        //底部填充
			leftDistance:20,
			pointWidth:5,               //坐标点宽度
			pointHeight:5,             //坐标点高度
			pointColor:"#ff0000",     //坐标点颜色
			lineColor:"#ffd43a",       //连接线颜色
			valueWidth:20,            //y轴数值宽度
			valueColor:"#000",       //y轴数值颜色
			timeWidth:20,             //x轴数值宽度
			timeColor:"#000",       //x轴数值颜色
			disvalue:true,             //是否显示y轴数值
			distime:true              //是否显示x轴数值
		}
		Object.extend(this.options, options || {});
  	},
	showPointGraphic:function(data,obj)
	{
			var This=this;
			var showPoints=new Array();
			var values=new Array();
			var times=new Array();
			This.points=data;
			This.count=data.value.length;

			for(var i=0;i<This.count;i++)
			{
				var showPoint=document.createElement("div");
                var spanValue=document.createElement("span");
                var spanTime=document.createElement("span");
				showPoint.height=This.points.value[i];
                showPoint.value=This.points.value[i];
                showPoint.time=This.points.time[i];
                // console.log(This.points.time[i]+"---"+This.points.value[i]);

				
				showPoint.style.backgroundColor=this.options.pointColor;
				showPoint.style.fontSize="0px";
				showPoint.style.position="absolute";
				showPoint.style.zIndex ="999";
				showPoint.style.width=this.options.pointWidth+"px";
				showPoint.style.height=this.options.pointHeight+"px";
				showPoint.style.top=this.options.topDistance+"px";
                
				spanValue.style.position="absolute";
				spanValue.style.width=this.options.valueWidth+"px";
				spanValue.style.textAlign="center";
				spanValue.style.color=this.options.valueColor;
				spanValue.style.zIndex ="999";

                spanTime.style.position="absolute";
				spanTime.style.width=this.options.timeWidth+"px";
				spanTime.style.textAlign="center";
				spanTime.style.color=this.options.timeColor;
				var timeHeight=15;
				var valueHeight=21;
				if(!this.options.disvalue) {
					spanValue.style.display="none";
					valueHeight=this.options.pointHeight;
				}
				if(!this.options.distime) {
					spanTime.style.display="none";
					timeHeight=0;
				}

				var left;
				if(showPoints.length!=0){
					left=parseInt(showPoints[showPoints.length-1].style.left)+parseInt(showPoints[showPoints.length-1].style.width)+this.options.barDistance;
				}
				else{
					left=this.options.leftDistance;
				}
				
				showPoint.style.left=left+"px";
                spanValue.style.left=left+parseInt((this.options.pointWidth-this.options.valueWidth)/2)+"px";
                spanTime.style.left=left+parseInt((this.options.pointWidth-this.options.timeWidth)/2)+"px";
												
				if(showPoint.height>this.options.maxHeight)
				{
					showPoint.height=this.options.maxHeight;
				}
				
		        spanValue.innerHTML=showPoint.value;
				spanTime.innerHTML=showPoint.time;
                        
                showPoints.push(showPoint);
                values.push(spanValue);
                times.push(spanTime);

                // y轴是负值(后台此时刻无数据)，则不画点
                if(This.points.value[i]<0)
                {
                	// console.log(This.points.value[i]+"----------");
                times[i].style.top=this.options.height-this.options.bottomDistance-timeHeight+"px";
                	
                	continue;
                }

				This.entity.appendChild(showPoint);
				This.entity.appendChild(spanValue);
				This.entity.appendChild(spanTime);
				
				var percentage=showPoints[i].height/this.options.maxHeight||0;
				var pointTop=(this.options.height-this.options.topDistance-this.options.bottomDistance-timeHeight-valueHeight)*percentage;
				showPoints[i].style.top=(this.options.height-this.options.bottomDistance-pointTop-timeHeight-this.options.pointHeight)+"px";
                values[i].style.top=(this.options.height-this.options.bottomDistance-pointTop-timeHeight-valueHeight)+"px";
                times[i].style.top=this.options.height-this.options.bottomDistance-timeHeight+"px";
			}
			function check(obj,i)
			{
				console.log("---"+i);
				console.log(obj[i].style.top);
				while(obj[i].style.top=="0px")
				{
					i++;
				}
				console.log(i+"---");
				return i;
			}
			var _leng=showPoints.length
			for(var i=0;i<_leng;i++)
			{
				// 检查是否为空（未测量），如果空则跳过
				function check(obj,i)
				{
					// console.log("---"+i);
					// console.log(obj[i].style.top);
					while(obj[i].style.top=="0px")
					{
						i++;
					}
					// console.log(i+"---");
					return i;
				}
				if(i>0)
				{
					// console.log("start_left---"+parseInt(showPoints[i-1].style.left));
					// console.log("start_top---"+parseInt(showPoints[i-1].style.top));
					// console.log("end_left---"+parseInt(showPoints[i].style.left));
					// console.log("end_top---"+parseInt(showPoints[i].style.top));

					// 假定两小时内至少测量一次，若两小时以上未测
					// 一次未测
					// if(parseInt(showPoints[i].style.top)==0)
					// {
					// 	// 两次未测
					// 	if(parseInt(showPoints[i+1].style.top)==0)
					// 	{
					// 		// 三次未测
					// 		if(parseInt(showPoints[i+2].style.top)==0)
					// 		{
					// 			This.drawLine(parseInt(showPoints[i-1].style.left),
					// 									parseInt(showPoints[i-1].style.top),
					// 									parseInt(showPoints[i+3].style.left),
					// 									parseInt(showPoints[i+3].style.top)
					// 									);
					// 		}
					// 		else
					// 		{
					// 			This.drawLine(parseInt(showPoints[i-1].style.left),
					// 								parseInt(showPoints[i-1].style.top),
					// 								parseInt(showPoints[i+2].style.left),
					// 								parseInt(showPoints[i+2].style.top)
					// 								);
					// 		}
					// 	}
					// 	else
					// 	{
					// 		This.drawLine(parseInt(showPoints[i-1].style.left),
					// 							parseInt(showPoints[i-1].style.top),
					// 							parseInt(showPoints[i+1].style.left),
					// 							parseInt(showPoints[i+1].style.top)
					// 							);
					// 	}
						
					// }
					// else
					// {
					// 	This.drawLine(parseInt(showPoints[i-1].style.left),
					// 							parseInt(showPoints[i-1].style.top),
					// 							parseInt(showPoints[i].style.left),
					// 							parseInt(showPoints[i].style.top)
					// 							);
					// }

					var j = check(showPoints,i);
					// console.log(j);
					// console.log(i);
					This.drawLine(parseInt(showPoints[i-1].style.left),
												parseInt(showPoints[i-1].style.top),
												parseInt(showPoints[j].style.left),
												parseInt(showPoints[j].style.top)
												);
				}
			}
			This.Constructor.call(This);
		},
		drawLine:function(startX,startY,endX,endY)
		{
			var xDirection=(endX-startX)/Math.abs(endX-startX);
			var yDirection=(endY-startY)/Math.abs(endY-startY);
			var xDistance=endX-startX;
			var yDistance=endY-startY;
			var xPercentage=1/Math.abs(endX-startX);
			var yPercentage=1/Math.abs(endY-startY);
			// console.log(endX+"---"+endY);
			// console.log(endY);
			// if(startY>410||endY>410)
			// {
			// 	return;
			// }
			if(startY==0||endY==0)
			{
				return;
			}
			if(Math.abs(startX-endX)>=Math.abs(startY-endY))
			{
				var _xnum=Math.abs(xDistance)
				for(var i=0;i<=_xnum;i++)
				{
					var point=document.createElement("div");
					point.style.position="absolute";
					point.style.backgroundColor=this.options.lineColor;
					point.style.fontSize="0";
					point.style.width="1px";
					point.style.height="1px";							
					
					startX+=xDirection;
					point.style.left=startX+this.options.pointWidth/2+"px";
					startY=startY+yDistance*xPercentage;
					point.style.top=startY+this.options.pointHeight/2+"px";
					this.entity.appendChild(point);
				}
			}
			else
			{
				var _ynum=Math.abs(yDistance)
				for(var i=0;i<=_ynum;i++)
				{
					var point=document.createElement("div");
					point.style.position="absolute";
					point.style.backgroundColor=this.options.lineColor;
					point.style.fontSize="0";
					point.style.width="1px";
					point.style.height="1px";							
					
					startY+=yDirection;
					point.style.top=startY+this.options.pointWidth/2+"px";
					startX=startX+xDistance*yPercentage;
					point.style.left=startX+this.options.pointHeight/2+"px";
					this.entity.appendChild(point);
				}
			}
		},
		Constructor:function()
		{
			this.entity.style.position="absolute";
			// this.pointBox.innerHTML="";
			this.pointBox.appendChild(this.entity);
		},
		check:function(obj)
		{
			console.log(obj[0]);
		}
}
