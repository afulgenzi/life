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
package com.fulg.life.web.server.controller;

import java.util.List;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.facade.BankAccountMovementFacade;
import com.fulg.life.web.client.controller.BankAccountMovementController;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class BankAccountMovementControllerImpl extends RemoteServiceServlet implements BankAccountMovementController {

	BankAccountMovementFacade facade = new BankAccountMovementFacade();

	public List<BankAccountMovementData> getAll() {
		return facade.getAll();
	}

	public List<BankAccountMovementData> getByMonth(int year, int month) {
		return facade.getByMonth(year, month);
	}

        public List<BankAccountMovementData> getByDescription(String description) {
            return facade.getByDescription(description);
        }

	public BankAccountMovementData updateMovement(BankAccountMovementData movement) {
		return facade.updateMovement(movement);
	}

	public Boolean deleteMovement(BankAccountMovementData movement) {
		return facade.deleteMovement(movement);
	}

	public BankAccountMovementData insertMovement(BankAccountMovementData movement) {
		return facade.insertMovement(movement);
	}
}
