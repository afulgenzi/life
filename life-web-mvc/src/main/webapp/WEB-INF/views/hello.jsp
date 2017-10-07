<html ng-app>
  <head>
<!--     <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js"></script> -->
<script src='${resourcePath}/js/jquery-1.8.3.min.js' type='text/javascript'></script>
<script src="${resourcePath}/js/angular.min.js"></script>
<script src="${resourcePath}/js/angular-resource.min.js"></script>
<script src="${resourcePath}/js/angular-sanitize.min.js"></script>
<script src="${resourcePath}/js/ngStorage.js"></script>
  </head>
  <body>
    <div>
      <label>Name:</label>
      <input type="text" ng-model="yourName" placeholder="Enter a name here">
      <hr>
      <h1>Hello {{yourName}}!</h1>
    </div>
  </body>
</html>