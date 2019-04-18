var SSOTool = function() {
};

SSOTool.getUrlParam = function(paras) {
	var url = window.decodeURIComponent(location.href);
	var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
	var paraObj = {};
	for (i = 0; j = paraString[i]; i++) {
		paraObj[j.substring(0, j.indexOf("="))] = j.substring(j
				.indexOf("=") + 1, j.length);
	}
	var returnValue = paraObj[paras];
	
	if(paras == "origUrl" && returnValue){
		var firstP = 0;
		$.each(paraObj, function(pk,pv){
			if(pk != "origUrl" && pk != "ticket"){
				firstP++;
				try{
					returnValue=SSOTool.trimEnd(returnValue,"#");
				}catch(ee){
					console.log(ee);
				}
				if(firstP==1){
					returnValue+="?"+pk+"="+pv;
				}else{
					returnValue+="&"+pk+"="+pv;
				}
			}
		});
	}
	if (!returnValue) {
		return "";
	} else {
		if(returnValue.sub(-1)=="#"){
			
			return SSOTool.trimEnd(returnValue,"#");
		}else{
			return returnValue;
		}
	}
};

SSOTool.trimString = function(str, trimstring){
	return str.replace(trimstring,'');
};

SSOTool.trimEnd = function(trimStr, trimmedStr){
   while(true){
	   var temindex = trimStr.indexOf(trimmedStr);
	   if(temindex == trimStr.length-1){
		   trimStr = trimStr.substring(0, temindex)
	   }else{
		   break;
	   }
   }
   
   return trimStr;
};