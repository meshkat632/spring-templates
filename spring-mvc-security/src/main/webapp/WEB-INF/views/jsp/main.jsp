<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="true"%>

<spring:eval expression="@securityUtils" var="secUtils" />

<html>
<head>
<script src="<c:url value="/resources/js/ectxml_2.0.0.js" />"></script>
<script src="<c:url value="/resources/js/main.js" />"></script>
</head>
<body>

	<c:url value="/j_spring_security_logout" var="logoutUrl" />

	<!-- csrt for log out-->
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>

	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>

	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h2>
			Welcome : ${pageContext.request.userPrincipal.name} | <a
				href="javascript:formSubmit()"> Logout</a>
		</h2>
	</c:if>
	<script>

    define('app',['ectxml/pubSubManager'], function(PubSubManager){
        var userId = undefined;
        var startPsm = function(onSuccess, onError, cometUrl){
            //var cometUrl = "http://se-cloud-01.ect-telecoms.de/comet-server/cometd";            
            //var cometUrl = "http://192.168.251.60:8080/comet-server/cometd";
            var cometUrl = "http://localhost:8081/comet-server/cometd";
            console.log('cometUrl', cometUrl);
        	var cometCredentials = {
                    owner : "ect",
                    name: '${secUtils.buildWebAuthToken(pageContext.request, false)}',                    
                    application : "cwp",
                    role : "USER"
            };
        	console.log("cometCredentials",cometCredentials);        	
            var psm = new PubSubManager(cometUrl);
            psm.start(cometCredentials, function(successful, passport, error, msg) {
                if (successful) {
                	console.log("psm connected");
                	console.log("passport:", passport);
                } else {
                	console.error("psm connection failed ! error:"+error);
                }
            });

        }

        startPsm();
    });


    require(['app']);
	</script>



</body>
</html>