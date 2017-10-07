package com.fulg.life.web.client.renderer;

import java.util.Date;
import java.util.List;

//import com.fulg.life.web.client.Life_Duty.GetValue;
import com.fulg.life.web.client.controller.BankAccountMovementController;
import com.fulg.life.web.client.controller.BankAccountMovementControllerAsync;
import com.fulg.life.web.client.ui.TableRes;
import com.fulg.life.data.BankAccountMovementData;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.IdentityColumn;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CellDutyRenderer {
	private int currentYear = 2013;
	private int currentMonth = 4;

	/*
	 * Get a cell value from a record.
	 * 
	 * @param <C> the cell type
	 */
	private static interface GetValue<C> {
		C getValue(BankAccountMovementData contact);
	}

	/*
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final BankAccountMovementControllerAsync movementController = GWT
			.create(BankAccountMovementController.class);

	// CellTable custom UI resource
	private CellTable.Resources tableRes = GWT.<TableRes> create(TableRes.class);

	/*
	 * Controls
	 */
	final ListBox monthCombo = new ListBox(false);
	@UiField(provided = true)
	CellTable<BankAccountMovementData> dutyGrid;
	final SimplePager pager = new SimplePager();
	final Button sendButton = new Button("Send");

//	@Override
	public void onModuleLoad() {

		dutyGrid = new CellTable<BankAccountMovementData>(10, tableRes);
		
		/*
		 * Current Year/Month
		 */
		currentYear = Integer.parseInt(DateTimeFormat.getFormat("d-M-yyyy").format(new Date()).split("-")[2]);

		currentMonth = Integer.parseInt(DateTimeFormat.getFormat("d-M-yyyy").format(new Date()).split("-")[1]);

		/*
		 * "Description" column
		 */
		Column<BankAccountMovementData, String> descriptionColumn = new Column<BankAccountMovementData, String>(
				new EditTextCell()) {
			@Override
			public String getValue(BankAccountMovementData duty) {
				if (duty != null) {
					return duty.getDescription();
				}
				return "";
			}
		};
		descriptionColumn.setFieldUpdater(new FieldUpdater<BankAccountMovementData, String>() {
			@Override
			public void update(int index, BankAccountMovementData object, String value) {
				object.setDescription(value);
				movementController.updateMovement(object, new AsyncCallback<BankAccountMovementData>() {
					@Override
					public void onSuccess(BankAccountMovementData result) {
						// Window.alert("UPDATED!!!!!\n\nForce RELOAD.");
						getRemoteMovementsByMonth(currentYear, currentMonth);
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("UPDATE FAILED!!!!!");
					}
				});
			}
		});

		/*
		 * "Date" column
		 */
		Column<BankAccountMovementData, Date> dateColumn = new Column<BankAccountMovementData, Date>(
				new DatePickerCell(DateTimeFormat.getFormat("dd/MM/yyyy"))) {
			@Override
			public Date getValue(BankAccountMovementData duty) {
				if (duty != null) {
					if (duty.getDate() != null) {
						return duty.getDate();
					}
				}
				return null;
			}
		};
		dateColumn.setFieldUpdater(new FieldUpdater<BankAccountMovementData, Date>() {
			@Override
			public void update(int index, BankAccountMovementData object, Date value) {
				object.setDate(value);
				movementController.updateMovement(object, new AsyncCallback<BankAccountMovementData>() {
					@Override
					public void onSuccess(BankAccountMovementData result) {
						// Window.alert("UPDATED!!!!!\n\nForce RELOAD.");
						getRemoteMovementsByMonth(currentYear, currentMonth);
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("UPDATE FAILED!!!!!");
					}
				});
			}
		});

		/*
		 * "In" column
		 */
		Column<BankAccountMovementData, String> inColumn = new Column<BankAccountMovementData, String>(
				new EditTextCell()) {
			@Override
			public String getValue(BankAccountMovementData duty) {
				if (duty != null) {
					if (duty.getAmount() != null) {
						if (BankAccountMovementData.IN.equals(duty.getEu())) {
							return duty.getAmount().toString();
						} else {
							return "";
						}
					}
				}
				return "";
			}
		};
		inColumn.setFieldUpdater(new FieldUpdater<BankAccountMovementData, String>() {
			@Override
			public void update(int index, BankAccountMovementData object, String value) {
				try {
					object.setAmount(Double.parseDouble(value));
				} catch (NumberFormatException ex) {
					String msg = "Cannot perform update. Not numeric value: " + value;
					Window.alert(msg);
					throw new RuntimeException("");
				}
				movementController.updateMovement(object, new AsyncCallback<BankAccountMovementData>() {
					@Override
					public void onSuccess(BankAccountMovementData result) {
						// Window.alert("UPDATED!!!!!\n\nForce RELOAD.");
						getRemoteMovementsByMonth(currentYear, currentMonth);
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("UPDATE FAILED!!!!!");
					}
				});
			}
		});

		/*
		 * "Out" column
		 */
		Column<BankAccountMovementData, String> outColumn = new Column<BankAccountMovementData, String>(
				new EditTextCell()) {
			@Override
			public String getValue(BankAccountMovementData duty) {
				if (duty != null) {
					if (duty.getAmount() != null) {
						if (BankAccountMovementData.OUT.equals(duty.getEu())) {
							return duty.getAmount().toString();
						} else {
							return "";
						}
					}
				}
				return "";
			}
		};
		outColumn.setFieldUpdater(new FieldUpdater<BankAccountMovementData, String>() {
			@Override
			public void update(int index, BankAccountMovementData object, String value) {
				try {
					object.setAmount(Double.parseDouble(value));
				} catch (NumberFormatException ex) {
					String msg = "Cannot perform update. Not numeric value: " + value;
					Window.alert(msg);
					throw new RuntimeException("");
				}
				movementController.updateMovement(object, new AsyncCallback<BankAccountMovementData>() {
					@Override
					public void onSuccess(BankAccountMovementData result) {
						// Window.alert("UPDATED!!!!!\n\nForce RELOAD.");
						getRemoteMovementsByMonth(currentYear, currentMonth);
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("UPDATE FAILED!!!!!");
					}
				});
			}
		});

		/*
		 * "Out" column
		 */
		Column<BankAccountMovementData, String> balanceColumn = new Column<BankAccountMovementData, String>(
				new TextCell()) {
			@Override
			public String getValue(BankAccountMovementData duty) {
				if (duty != null) {
					if (duty.getAmount() != null) {
						return duty.getBalanceAfter().toString();
					}
				}
				return "";
			}
		};

		// "Edit" column
		Column<BankAccountMovementData, ActionCell> editColumn = (new IdentityColumn(
				new ActionCell<BankAccountMovementData>("edit..", new ActionCell.Delegate<BankAccountMovementData>() {
					public void execute(BankAccountMovementData object) {
						// List<BankAccountMovementData> list = new
						// ArrayList<BankAccountMovementData>(table.getVisibleItems());
						// for(int i = 0; i < list.size(); i ++){
						// if(object.getFirstname().equals(list.get(i).getFirstname())){
						// list.remove(i);
						// break;
						// }
						// }
						// table.setRowData(list);
						Window.alert("EDIT!!!!!");
					}
				})));

		// "Edit" column
		Column<BankAccountMovementData, ActionCell> removeColumn = (new IdentityColumn(
				new ActionCell<BankAccountMovementData>("remove", new ActionCell.Delegate<BankAccountMovementData>() {
					public void execute(BankAccountMovementData object) {
						// List<BankAccountMovementData> list = new
						// ArrayList<BankAccountMovementData>(table.getVisibleItems());
						// for(int i = 0; i < list.size(); i ++){
						// if(object.getFirstname().equals(list.get(i).getFirstname())){
						// list.remove(i);
						// break;
						// }
						// }
						// table.setRowData(list);
						Window.alert("REMOVE!!!!!");
					}
				})));

		// List<BankAccountMovementData> BankAccountMovementDataList = new
		// ArrayList<BankAccountMovementData>();
		// BankAccountMovementData duty;
		//
		// duty = new BankAccountMovementData();
		// duty.setDescription("DutyDescription1");
		// BankAccountMovementDataList.add(duty);
		//
		// duty = new BankAccountMovementData();
		// duty.setDescription("DutyDescription2");
		// BankAccountMovementDataList.add(duty);

		monthCombo.addItem("January", "1");
		monthCombo.addItem("Febryary", "2");
		monthCombo.addItem("March", "3");
		monthCombo.addItem("April", "4");
		monthCombo.addItem("May", "5");
		monthCombo.addItem("June", "6");
		monthCombo.addItem("July", "7");
		monthCombo.addItem("August", "8");
		monthCombo.addItem("September", "9");
		monthCombo.addItem("October", "10");
		monthCombo.addItem("November", "11");
		monthCombo.addItem("December", "12");
		monthCombo.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				int selectedIndex = monthCombo.getSelectedIndex();
				if (selectedIndex > 0) {
					currentMonth = Integer.parseInt(monthCombo.getValue(selectedIndex));
					getRemoteMovementsByMonth(currentYear, currentMonth);
				}
			}
		});

		// dutyGrid.setColumnWidth(titleColumn, "200");
		dutyGrid.addColumn(descriptionColumn, "Description");
		dutyGrid.addColumn(dateColumn, "Date");
		dutyGrid.addColumn(outColumn, "Out");
		dutyGrid.addColumn(inColumn, "In");
		dutyGrid.addColumn(balanceColumn, "Balance");
		// dutyGrid.addColumn(editColumn, "Edit");
		dutyGrid.addColumn(removeColumn, "Remove");
		dutyGrid.setRowCount(0, true);
		dutyGrid.setVisible(false);
		// for (int ind=0;ind<dutyGrid.getColumnCount();ind++){
		// dutyGrid.getHeader(ind).setHeaderStyleNames("gwt-CellTableHeader");
		// }

		pager.setDisplay(dutyGrid);
		pager.setPageSize(1000);

		VerticalPanel vp = new VerticalPanel();
		vp.add(monthCombo);
		vp.add(dutyGrid);
		vp.add(pager);

		sendButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getRemoteMovementsByMonth(currentYear, currentMonth);
			}
		});
		final Label errorLabel = new Label("Error label");

		// Add it to the root panel.
		RootPanel.get("gridContainer").add(vp);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		monthCombo.setItemSelected(currentMonth - 1, true);
		getRemoteMovementsByMonth(currentYear, currentMonth);
	}

	/**
	 * Add a column with a header.
	 * 
	 * @param <C>
	 *            the cell type
	 * @param cell
	 *            the cell used to render the column
	 * @param headerText
	 *            the header string
	 * @param getter
	 *            the value getter for the cell
	 */
	private <C> Column<BankAccountMovementData, C> addColumn(Cell<C> cell, String headerText, final GetValue<C> getter,
			FieldUpdater<BankAccountMovementData, C> fieldUpdater) {
		Column<BankAccountMovementData, C> column = new Column<BankAccountMovementData, C>(cell) {
			@Override
			public C getValue(BankAccountMovementData object) {
				return getter.getValue(object);
			}
		};
		column.setFieldUpdater(fieldUpdater);
		// if (cell instanceof AbstractEditableCell<?, ?>) {
		// editableCells.add((AbstractEditableCell<?, ?>) cell);
		// }
		dutyGrid.addColumn(column, headerText);
		return column;
	}

	private void getRemoteMovementsByMonth(int year, int month) {
		System.out.println("getRemoteMovementsByMonth(" + year + ", " + month + ")");
		System.out.println("dutyGrid.getStyleName: "+dutyGrid.getStyleName());
		System.out.println("dutyGrid.getStyleName: "+dutyGrid.getHeader(0).getHeaderStyleNames());
		sendButton.setEnabled(false);
		// dutyGrid.setVisible(false);
		movementController.getByMonth(year, month, new AsyncCallback<List<BankAccountMovementData>>() {
			@Override
			public void onSuccess(List<BankAccountMovementData> result) {
				dutyGrid.setRowData(0, result);
				dutyGrid.setRowCount(result.size());
				sendButton.setEnabled(true);
				pager.setDisplay(dutyGrid);
				dutyGrid.setVisible(true);
			}

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				sendButton.setEnabled(true);
				dutyGrid.setVisible(true);
			}
		});
	}

}
