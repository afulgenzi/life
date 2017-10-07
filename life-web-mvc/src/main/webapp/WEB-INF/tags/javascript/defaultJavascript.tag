<%@ tag body-content="empty"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"/> -->
<script src='${resourcePath}/js/jquery-1.8.3.min.js' type='text/javascript'></script>
<script src="${resourcePath}/js/angular.min.js"></script>
<%--<script src="${resourcePath}/js/angular-touch.js"></script>--%>
<script src="${resourcePath}/js/angular-resource.min.js"></script>
<%-- <script src="${resourcePath}/js/angular-sanitize.min.js"></script> --%>
<%-- <script src="${resourcePath}/js/ngStorage.js"></script> --%>
<script src="${resourcePath}/js/less-1.5.0.min.js" type="text/javascript"></script>
<script src="${resourcePath}/js/life.js" type="text/javascript"></script>

<!-- bootstrap -->
<script src="${resourcePath}/js/ui-bootstrap-tpls-0.10.0.js" type="text/javascript"></script>
<script src="${resourcePath}/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${resourcePath}/js/bootstrap-treeview.min.js" type="text/javascript"></script>
<%--<script src="${resourcePath}/js/bootstrap-table-locale-all.js" type="text/javascript"></script>--%>
<!-- <link rel="stylesheet" href="http://tarruda.github.com/bootstrap-datetimepicker/assets/css/bootstrap-datetimepicker.min.css" type="text/css" media="screen" > -->
<link rel="stylesheet" href="${resourcePath}/css/bootstrap.css">
<%--<link rel="stylesheet" href="${resourcePath}/css/bootstrap-table.css">--%>
<link rel="stylesheet" href="${resourcePath}/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="${resourcePath}/css/life-overlay.css">

<script>
var LifeJs = {
	Common : {
		jsonPath: '${request.contextPath}'
	}
}
</script>

<%-- Angular --%>
<script src="${resourcePath}/js/app/app.js"></script>
<script src="${resourcePath}/js/app/controllers.js"></script>
<%--<script src="${resourcePath}/js/app/import-movements-controllers.js"></script>--%>
<script src="${resourcePath}/js/app/services.js"></script>
<%--<script src="${resourcePath}/js/app/common-services.js"></script>--%>
<script src="${resourcePath}/js/app/directives.js"></script>
<script src="${resourcePath}/js/app/filters.js"></script>
<!-- <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>  -->
<!-- <script type="text/javascript" src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/js/bootstrap.min.js"></script> -->
<!-- <script type="text/javascript" src="http://tarruda.github.com/bootstrap-datetimepicker/assets/js/bootstrap-datetimepicker.min.js"></script> -->
<!-- <script type="text/javascript" src="http://tarruda.github.com/bootstrap-datetimepicker/assets/js/bootstrap-datetimepicker.pt-BR.js"></script> -->
    
<script type="text/javascript">

	// Document READY!!!
	$(document).ready(function() {
		$.ajaxSetup({ cache: false });
		
		$('.systemSelector').on('change', function(e) {
			if (this.selectedIndex > 0){
				var selectedCatalogSystem = this.value;
				// 			e.preventDefault();
				changeCatalogSystem(selectedCatalogSystem);
				$('.tableSelector').clear();
			}
		});
	});

	// Change Catalog System
	function changeCatalogSystem(catalogSystem) {
		jQuery.getJSON(
				'http://localhost:18080/springmvc/tableList?catalogSystem='
						+ catalogSystem, function(thisResponse) {

							 var $model = $('select.tableSelector');
					        $model.empty().append(function() {
					            var output = '';
					            $.each(thisResponse, function(key, value) {
					                output += '<option value=\"\">' + value + '</option>';
					            });
					            return output;
					        });
					        
				}).done(function() {
			//
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("error " + textStatus);
			alert("incoming Text " + jqXHR.responseText);
		}).always(function() {
			//
		});
	}
	
</script>
