<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<div class='ng-modal' ng-show='show'>
  <div class='ng-modal-overlay' ng-click='hideModal()'></div>
  <div class='ng-modal-dialog' ng-style='dialogStyle'>
    <div class='ng-modal-close' ng-click='hideModal()'>X</div>
    <div class='ng-modal-dialog-content' ng-transclude></div>
  </div>
</div>