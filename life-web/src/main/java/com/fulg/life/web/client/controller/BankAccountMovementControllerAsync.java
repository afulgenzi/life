/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.fulg.life.web.client.controller;

import java.util.List;

import com.fulg.life.data.BankAccountMovementData;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BankAccountMovementControllerAsync {
	public void getAll(AsyncCallback<List<BankAccountMovementData>> callback);
	
	public void getByMonth(int year, int month, AsyncCallback<List<BankAccountMovementData>> callback);
	
        public void getByDescription(String description, AsyncCallback<List<BankAccountMovementData>> callback);
        
	public void updateMovement(BankAccountMovementData movement, AsyncCallback<BankAccountMovementData> callback);
	
	public void deleteMovement(BankAccountMovementData movement, AsyncCallback<Boolean> callback);
	
	public void insertMovement(BankAccountMovementData movement, AsyncCallback<BankAccountMovementData> callback);
}
