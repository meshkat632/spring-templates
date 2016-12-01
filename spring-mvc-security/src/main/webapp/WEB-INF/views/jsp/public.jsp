<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="true"%>
<spring:eval expression="@securityUtils" var="secUtils" />

<html>
<head>
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>    
    <script src="<c:url value="/resources/js/ectxml_2.0.0.js" />"></script>
    <script src="<c:url value="/resources/js/main.js" />"></script>
    <script>

    define('app',['ectxml/pubSubManager'], function(PubSubManager){
    	
		var csrfToken = '${_csrf.token}';
        var userId = undefined;
        var startPsm = function(onSuccess, onError, cometUrl){
            //var cometUrl = "http://se-cloud-01.ect-telecoms.de/comet-server/cometd";            
            //var cometUrl = "http://192.168.251.60:8080/comet-server/cometd";
            var cometUrl = "http://localhost:8081/comet-server/cometd";
            //var cometUrl = "http://192.168.143.83:8080/comet-server/cometd";
            console.log('cometUrl', cometUrl);
        	var cometCredentials = {
                    owner : "ect",
                    name: '${secUtils.buildWebAuthToken(pageContext.request, true)}',
                    application : "cwp",
                    role : "USER"
            };
        	console.log("cometCredentials",cometCredentials);        	
            var psm = new PubSubManager(cometUrl);
            psm.start(cometCredentials, function(successful, passport, error, msg) {
                if (successful) {
                	console.log("psm connected");
                	console.log("passport", passport);
                	console.log("csrfToken", csrfToken);
                	
                	var state = getParameterByName('state')||"createNewEntity";
                	console.log("state", state);
                	$("#applicationState").append("psm connected. state:<b>"+state+"</b>");
                	
                } else {
                	console.error("psm connection failed");
                }
            });

        }

        startPsm();
    });


    require(['app']);
	</script>    
    
</head>
<body>	  
<div id=applicationState></div> 
</body>
</html>