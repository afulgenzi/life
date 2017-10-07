<div class="col-xs-12" style="margin-bottom: 20px; background-color: #85b5ed; padding-left: 0; padding-right: 0;">
    <div class="modal-body">
        <div class="form-row col-xs-12">
            <ul class="nav nav-pills" style="margin-bottom: 20px">
                <li style="width: 49%; text-align: center">
                    <a data-toggle="pill" href="#createNewCategoryPane" ng-click="loadExistingCategoryTreeForSelectingParent('selectParentCategoryTreeForNew')">
                        Create New
                    </a>
                </li>
                <li style="width: 49%; text-align: center; float: right">
                    <a data-toggle="pill" href="#selectExistingCategoryPane" ng-click="loadExistingCategoryList();loadExistingCategoryTreeForSelectingParent('selectParentCategoryTreeForEdit')">
                        Select Existing
                    </a>
                </li>
            </ul>
        </div>
        <div class="tab-content col-xs-12">

            <!-- Create New Category -->
            <div id="createNewCategoryPane" class="tab-pane fade">
                <div class="form-row col-xs-12 col-sm-6" style="">
                    <!--Code -->
                    <div class="col-xs-12" style="padding-left: 0; padding-right: 0">
                        <div>
                            <span class="aside-filter-item">Code</span>
                        </div>
                        <div>
                            <input class="form-control input-lg life-input input-alphanum" ng-model="categoryForm.category.code"/>
                        </div>
                    </div>
                    <!--Title -->
                    <div class="col-xs-12" style="padding-left: 0; padding-right: 0; margin-top: 12px">
                        <div>
                            <span class="aside-filter-item">Title</span>
                        </div>
                        <div>
                            <input class="form-control input-lg life-input" ng-model="categoryForm.category.title"/>
                        </div>
                    </div>
                    <!--Frequency -->
                    <div class="col-xs-12" style="padding-left: 0; padding-right: 0; margin-top: 12px">
                        <div class="col-xs-12" style="padding-left: 0; padding-right: 0">
                            <span class="aside-filter-item">Repeat every</span>
                        </div>
                        <div class="col-xs-6" style="padding-left: 0; padding-right: 0;">
                            <input class="form-control input-lg life-input" ng-model="categoryForm.category.frequency.frequencyInterval" placeholder="interval"/>
                        </div>
                        <div class="col-xs-6" style="padding-left: 0; padding-right: 0;">
                            <select style="font-size:1.2em;color:#339;background-color:lightyellow"
                                    class="form-control input-lg clickable"
                                    ng-model="categoryForm.category.frequency.frequencyType"
                                    ng-options="ft.intervalUnit for ft in frequencyTypes | orderBy:'displayName'">
                            </select>
                        </div>
                    </div>
                    <div style="margin-top: 12px;">
                        <div class="col-xs-12" style="padding-left: 0; padding-right: 0; margin-top: 12px">
                            <span class="aside-filter-item">Repeat Until</span>
                        </div>
                        <div class="col-xs-12" style="padding-left: 0; padding-right: 0">
                            <input type="text" class="form-control input-lg ng-pristine ng-valid ng-valid-required"
                                   datepicker-popup="dd-MMMM-yyyy" ng-model="categoryForm.category.frequency.frequencyEndDate" is-open="opened14" min="2000-01-01" max="'2020-12-31'"
                                   datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true" close-text="Close"
                                   required="required" ng-click="open($event,'opened14')"
                                   ng-change="setDefaultTime(inputSearchForm.endDate);"/>
                        </div>
                    </div>
                    <!--No Supercategory -->
                    <div class="col-xs-12" style="padding-left: 0; padding-right: 0; margin-top: 12px">
                        <div class="life-checkbox" style="margin-top: -10px">
                            <label class="checkbox-inline">
                                <input class="checkbox-inline" type="checkbox"
                                       ng-model="categoryForm.noSupercategory"
                                       ng-checked="toggleNoSupercategoryCheckbox(categoryForm.noSupercategory, 'selectParentCategoryTreeForNew')"
                                       style="margin-top: 8px; margin-left: -15px"
                                />
                                <span class="account-checkbox-label" style="margin-top: 45px">No Supercategory</span>
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-row col-xs-12 col-sm-6" style="">
                    <div>
                        <span class="aside-filter-item">Parent Category</span>
                    </div>
                    <div>
                        <div id="selectParentCategoryTreeForNew" style="height: 400px; overflow-y: scroll"></div>
                    </div>
                </div>
                <div class="form-row" style="text-align: right">
                    <button class="btn btn-primary" ng-click="saveCategory('selectParentCategoryTreeForNew', categoryForm)">Create</button>
                </div>
            </div>

            <!-- Search Existing Category -->
            <div id="selectExistingCategoryPane" class="tab-pane fade" >
                <div class="form-row col-xs-12 col-sm-12 col-lg-6" style="height: 300px; overflow-y: scroll">
                    <table id="box-table-a">
                        <thead>
                        <tr>
                            <th>Code</th>
                            <th>Title</th>
                            <th>Frequency</th>
                        </tr>
                        </thead>
                        <tr ng-repeat="cat in categories | orderBy:['path']"
                            ng-click="selectedCategory(cat)">
                            <td class="aside-filter-item" style="display: table-cell">{{cat.code}}</td>
                            <td class="aside-filter-item" style="display: table-cell">{{cat.path}}</td>
                            <td ng-hide="cat.subcategories.length > 0" class="aside-filter-item" style="display: table-cell">{{cat.frequency.frequencyInterval != 0 && cat.frequency.frequencyInterval || ''}} {{cat.frequency.frequencyType.intervalUnit}}</td>
                            <td ng-show="cat.subcategories.length > 0" class="aside-filter-item" style="display: table-cell">-</td>
                        </tr>
                    </table>
                </div>
                <div class="form-row col-xs-12 col-sm-6 col-lg-3" style="">
                    <!--Code -->
                    <div class="col-xs-12" style="padding-left: 0; padding-right: 0">
                        <div>
                            <span class="aside-filter-item">Code</span>
                        </div>
                        <div>
                            <input class="form-control input-lg life-input input-alphanum" ng-model="categoryForm.category.code"/>
                        </div>
                    </div>
                    <!--Title -->
                    <div class="col-xs-12" style="padding-left: 0; padding-right: 0; margin-top: 12px">
                        <div>
                            <span class="aside-filter-item">Title</span>
                        </div>
                        <div>
                            <input class="form-control input-lg life-input" ng-model="categoryForm.category.title"/>
                        </div>
                    </div>
                    <!--Frequency -->
                    <div class="col-xs-12" style="padding-left: 0; padding-right: 0; margin-top: 12px">
                        <div class="col-xs-12" style="padding-left: 0; padding-right: 0">
                            <span class="aside-filter-item">Repeat every</span>
                        </div>
                        <div class="col-xs-6" style="padding-left: 0; padding-right: 0">
                            <input ng-disabled="categoryForm.category.subcategories.length > 0" class="form-control input-lg life-input" ng-model="categoryForm.category.frequency.frequencyInterval"/>
                        </div>
                        <div class="col-xs-6" style="padding-left: 0; padding-right: 0">
                            <select style="font-size:1.2em;color:#339;background-color:lightyellow"
                                    class="form-control input-lg clickable"
                                    ng-model="categoryForm.category.frequency.frequencyType"
                                    ng-disabled="categoryForm.category.subcategories.length > 0"
                                    ng-options="ft.intervalUnit for ft in frequencyTypes | orderBy:'displayName'">
                            </select>
                        </div>
                    </div>
                    <div class="col-xs-12" style="padding-left: 0; padding-right: 0; margin-top: 12px">
                        <div class="col-xs-12" style="padding-left: 0; padding-right: 0">
                            <span class="aside-filter-item">Repeat Until</span>
                        </div>
                        <div class="col-xs-12" style="padding-left: 0; padding-right: 0">
                            <input type="text" class="form-control input-lg ng-pristine ng-valid ng-valid-required"
                                   datepicker-popup="dd-MMMM-yyyy" ng-model="categoryForm.category.frequency.frequencyEndDate" is-open="opened15" min="2000-01-01" max="'2020-12-31'"
                                   datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true" close-text="Close"
                                   required="required" ng-click="open($event,'opened15')"
                                   ng-change="setDefaultTime(inputSearchForm.endDate);"/>
                        </div>
                    </div>
                    <!--No Supercategory -->
                    <div class="life-checkbox col-xs-12" style="padding-left: 0; padding-right: 0; margin-top: 12px">
                        <label class="checkbox-inline" style="margin-top: -10px">
                            <input class="checkbox-inline" type="checkbox"
                                   ng-model="categoryForm.noSupercategory"
                                   ng-checked="toggleNoSupercategoryCheckbox(categoryForm.noSupercategory, 'selectParentCategoryTreeForEdit')"
                                   style="margin-top: 8px; margin-left: -15px"
                            />
                            <span class="account-checkbox-label" style="margin-top: 45px">No Supercategory</span>
                        </label>
                    </div>
                </div>
                <div class="form-row col-xs-12 col-sm-6 col-lg-3" style="">
                    <div>
                        <span class="aside-filter-item">Parent Category</span>
                    </div>
                    <div>
                        <div ng-disabled="categoryForm.noSupercategory" id="selectParentCategoryTreeForEdit" style="height: 400px; overflow-y: scroll; background-color: #fff"></div>
                    </div>
                </div>
                <div class="form-row col-xs-12" style="text-align: right">
                    <button class="btn btn-warning" ng-click="deleteCategory('selectParentCategoryTreeForEdit', categoryForm)">Delete</button>
                    <button class="btn btn-primary" ng-click="saveCategory('selectParentCategoryTreeForEdit', categoryForm)">Save</button>
                </div>
            </div>
        </div>
    </div>
</div>
