package com.fulg.life.web.client;

import java.util.Date;
import java.util.List;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.web.client.controller.BankAccountMovementController;
import com.fulg.life.web.client.controller.BankAccountMovementControllerAsync;
import com.fulg.life.web.client.ui.TableRes;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
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
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.user.datepicker.client.DateBox;

public class Life_Duty implements EntryPoint {
    private int currentYear = 2013;
    private int currentMonth = 4;

    /*
     * Get a cell value from a record.
     * 
     * @param <C> the cell type
     */
    // private static interface GetValue<C> {
    // C getValue(BankAccountMovementData contact);
    // }

    /*
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final BankAccountMovementControllerAsync movementController = GWT
            .create(BankAccountMovementController.class);

    // CellTable custom UI resource
    private CellTable.Resources tableRes = GWT.<TableRes> create(TableRes.class);

    private final String SEARCHINFIELD_DESCRIPTION = "Description";
    private final String SEARCHINFIELD_DATE = "Date";
    private final String SEARCHINFIELD_AMOUNT = "Amount";

    /*
     * Controls
     */
    final ListBox yearCombo = new ListBox(false);
    final ListBox monthCombo = new ListBox(false);
    @UiField(provided = true)
    CellTable<BankAccountMovementData> dutyGrid;
    final SimplePager pager = new SimplePager();
    final Button newButton = new Button("New");
    final DialogBox dialogBox = new DialogBox();
    final Button prevButton = new Button("Prev");
    final Button nextButton = new Button("Next");
    final HTML searchLabel = new HTML("<br><b>Search</b>");
    final TextBox searchText = new TextBox();
    final HTML searchInFieldLabel = new HTML("<br><b>in field</b>");
    final ListBox searchInFieldCombo = new ListBox(false);
    final Button searchButton = new Button("Go");

    // @Override
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
                        Window.alert("UPDATE FAILED!!!!!\n" + caught.getMessage());
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
                        Window.alert("UPDATE FAILED!!!!!\n" + caught.getMessage());
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
                        Window.alert("UPDATE FAILED!!!!!\n" + caught.getMessage());
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
                        Window.alert("UPDATE FAILED!!!!!\n" + caught.getMessage());
                    }
                });
            }
        });

        /*
         * "Balance" column
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
                        openDialogBox(object);
                    }
                })));

        // "Remove" column
        Column<BankAccountMovementData, ActionCell> removeColumn = (new IdentityColumn(
                new ActionCell<BankAccountMovementData>("remove", new ActionCell.Delegate<BankAccountMovementData>() {
                    public void execute(BankAccountMovementData movement) {
                        movementController.deleteMovement(movement, new AsyncCallback<Boolean>() {
                            @Override
                            public void onSuccess(Boolean result) {
                                // Window.alert("REMOVE result: " + result);
                                getRemoteMovementsByMonth(currentYear, currentMonth);
                            }

                            @Override
                            public void onFailure(Throwable caught) {
                                Window.alert("Error occurred during removal!");
                            }
                        });
                    }
                })));

        // Month Selection Control
        for (int ind = 2010; ind < 2020; ind++) {
            yearCombo.addItem(String.valueOf(ind), String.valueOf(ind));
        }
        yearCombo.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent event) {
                int selectedIndex = yearCombo.getSelectedIndex();
                if (selectedIndex > 0) {
                    currentYear = Integer.parseInt(yearCombo.getValue(selectedIndex));
                    getRemoteMovementsByMonth(currentYear, currentMonth);
                }
            }
        });

        // Month Selection Control
        monthCombo.addItem("January", "1");
        monthCombo.addItem("February", "2");
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
                // if (selectedIndex > 0) {
                currentMonth = Integer.parseInt(monthCombo.getValue(selectedIndex));
                getRemoteMovementsByMonth(currentYear, currentMonth);
                // }
            }
        });

        searchInFieldCombo.addItem(SEARCHINFIELD_DESCRIPTION, SEARCHINFIELD_DESCRIPTION.toLowerCase());
        searchInFieldCombo.addItem(SEARCHINFIELD_DATE, SEARCHINFIELD_DATE.toLowerCase());
        searchInFieldCombo.addItem(SEARCHINFIELD_AMOUNT, SEARCHINFIELD_AMOUNT.toLowerCase());

        // Add Columns to Grid
        dutyGrid.addColumn(descriptionColumn, "Description");
        dutyGrid.addColumn(dateColumn, "Date");
        dutyGrid.addColumn(outColumn, "Out");
        dutyGrid.addColumn(inColumn, "In");
        dutyGrid.addColumn(balanceColumn, "Balance");
        dutyGrid.addColumn(editColumn, "Edit");
        dutyGrid.addColumn(removeColumn, "Remove");
        dutyGrid.setRowCount(0, true);
        dutyGrid.setVisible(false);

        pager.setDisplay(dutyGrid);
        pager.setPageSize(1000);

        HorizontalPanel hp = new HorizontalPanel();
        hp.add(prevButton);
        hp.add(nextButton);
        hp.add(yearCombo);
        hp.add(monthCombo);
        hp.add(newButton);
        hp.add(searchLabel);
        hp.add(searchText);
        hp.add(searchInFieldLabel);
        hp.add(searchInFieldCombo);
        hp.add(searchButton);

        VerticalPanel vp = new VerticalPanel();
        vp.add(hp);
        vp.add(dutyGrid);
        vp.add(pager);

        final Label errorLabel = new Label("Error label");

        prevButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (currentMonth == 1) {
                    currentYear--;
                    currentMonth = 12;
                } else {
                    currentMonth--;
                }
                getRemoteMovementsByMonth(currentYear, currentMonth);
            }
        });

        nextButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (currentMonth == 12) {
                    currentYear++;
                    currentMonth = 1;
                } else {
                    currentMonth++;
                }
                getRemoteMovementsByMonth(currentYear, currentMonth);
            }
        });

        searchButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
//                Window.alert("Search for '" + searchText.getValue() + "' in field '"
//                        + searchInFieldCombo.getValue(searchInFieldCombo.getSelectedIndex()) + "'");
                getRemoteMovementsByField(searchText.getValue(), SEARCHINFIELD_DESCRIPTION);
            }
        });

        newButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                openDialogBox(null);
                newButton.setEnabled(false);
            }
        });

        // Add it to the root panel.
        RootPanel.get("gridContainer").add(vp);
        RootPanel.get("errorLabelContainer").add(errorLabel);

        setSelectedItem(yearCombo, String.valueOf(currentYear));
        setSelectedItem(monthCombo, String.valueOf(currentMonth));
        monthCombo.setItemSelected(currentMonth - 1, true);
        getRemoteMovementsByMonth(currentYear, currentMonth);
    }

    private void setSelectedItem(ListBox listBox, String value) {
        for (int ind = 0; ind < listBox.getItemCount(); ind++) {
            if (listBox.getItemText(ind).equals(value)) {
                listBox.setItemSelected(ind, true);
                break;
            }
        }
    }

    // Get BankAccountMovements from Server
    private void getRemoteMovementsByMonth(int year, int month) {
        newButton.setEnabled(false);
        prevButton.setEnabled(false);
        nextButton.setEnabled(false);
        searchButton.setEnabled(false);
        movementController.getByMonth(year, month, new AsyncCallback<List<BankAccountMovementData>>() {
            @Override
            public void onSuccess(List<BankAccountMovementData> result) {
                onSearchSuccess(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                onSearchFailure(caught);
            }
        });
    }

    // Get BankAccountMovements from Server
    private void getRemoteMovementsByField(String fieldValue, String fieldName) {
        newButton.setEnabled(false);
        prevButton.setEnabled(false);
        nextButton.setEnabled(false);
        searchButton.setEnabled(false);
        if (SEARCHINFIELD_DESCRIPTION.equalsIgnoreCase(fieldName)) {
            movementController.getByDescription(fieldValue, new AsyncCallback<List<BankAccountMovementData>>() {
                @Override
                public void onSuccess(List<BankAccountMovementData> result) {
                    onSearchSuccess(result);
                }

                @Override
                public void onFailure(Throwable caught) {
                    onSearchFailure(caught);
                }
            });
        }
    }

    public void onSearchSuccess(List<BankAccountMovementData> result) {
        dutyGrid.setRowData(0, result);
        dutyGrid.setRowCount(result.size());
        pager.setDisplay(dutyGrid);
        dutyGrid.setVisible(true);
        newButton.setEnabled(true);
        prevButton.setEnabled(true);
        nextButton.setEnabled(true);
        searchButton.setEnabled(true);
        setSelectedItem(yearCombo, String.valueOf(currentYear));
        monthCombo.setItemSelected(currentMonth - 1, true);
    }

    public void onSearchFailure(Throwable caught) {
        caught.printStackTrace();
        newButton.setEnabled(true);
        prevButton.setEnabled(true);
        nextButton.setEnabled(true);
        searchButton.setEnabled(true);
        dutyGrid.setVisible(true);
        Window.alert(caught.getMessage());
    }

    private void openDialogBox(BankAccountMovementData movement) {
        // Buttons
        final Button createButton = new Button("Create");
        createButton.getElement().setId("createButton");
        final Button saveButton = new Button("Save");
        saveButton.getElement().setId("saveButton");
        final Button cancelButton = new Button("Cancel");
        cancelButton.getElement().setId("cancelButton");

        TextBox description = new TextBox();
        DateBox date = new DateBox();
        DoubleBox amount = new DoubleBox();
        ListBox eu = new ListBox(false);
        eu.addItem("Entrata", "E");
        eu.addItem("Uscita", "U");
        eu.setSelectedIndex(1);
        DateBox until = new DateBox();

        // Dialog Panel
        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(new HTML("<br><b>Description:</b>"));
        dialogVPanel.add(description);
        dialogVPanel.add(new HTML("<br><b>Date:</b>"));
        dialogVPanel.add(date);
        dialogVPanel.add(new HTML("<br><b>Amount:</b>"));
        dialogVPanel.add(amount);
        dialogVPanel.add(new HTML("<br><b>EU:</b>"));
        dialogVPanel.add(eu);
        dialogVPanel.add(new HTML("<br><b>Until:</b>"));
        dialogVPanel.add(until);
        dialogVPanel.add(new HTML("<br>"));
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        HorizontalPanel hp = new HorizontalPanel();
        hp.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
        if (movement == null) {
            hp.add(createButton);
        } else {
            description.setValue(movement.getDescription());
            date.setValue(movement.getDate());
            amount.setValue(movement.getAmount());
            for (int ind = 0; ind < eu.getItemCount(); ind++) {
                if (eu.getValue(ind).equals(movement.getEu())) {
                    eu.setSelectedIndex(ind);
                    break;
                }
            }
            hp.add(saveButton);
        }
        hp.add(new HTML("&nbsp;"));
        hp.add(cancelButton);
        dialogVPanel.add(hp);

        // Dialog Box
        dialogBox.setText("Remote Procedure Call");
        dialogBox.setAnimationEnabled(true);
        dialogBox.setWidget(dialogVPanel);
        dialogBox.show();

        // Add a handler to close the DialogBox
        // saveButton.addClickHandler(new ClickHandler() {
        // public void onClick(ClickEvent event) {
        // dialogBox.hide();
        // newButton.setEnabled(true);
        // }
        // });

        // Add a handler to close the DialogBox
        createButton.addClickHandler(new MovementCreateHandler(description, date, amount, eu, until));
        saveButton.addClickHandler(new MovementSaveHandler(movement, description, date, amount, eu));

        // Add a handler to close the DialogBox
        cancelButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
                newButton.setEnabled(true);
            }
        });
    }

    private class MovementSaveHandler implements ClickHandler {
        BankAccountMovementData movement;
        TextBox description;
        DateBox date;
        DoubleBox amount;
        ListBox eu;

        public MovementSaveHandler(BankAccountMovementData movement, TextBox description, DateBox date,
                DoubleBox amount, ListBox eu) {
            this.movement = movement;
            this.description = description;
            this.date = date;
            this.amount = amount;
            this.eu = eu;
        }

        @Override
        public void onClick(ClickEvent event) {
            movement.setDescription(description.getValue());
            movement.setDate(date.getValue());
            movement.setAmount(amount.getValue());
            movement.setEu(eu.getValue(eu.getSelectedIndex()));
            movementController.updateMovement(movement, new AsyncCallback<BankAccountMovementData>() {
                @Override
                public void onSuccess(BankAccountMovementData result) {
                    // Window.alert("UPDATED!!!!!\n\nForce RELOAD.");
                    getRemoteMovementsByMonth(currentYear, currentMonth);
                }

                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("UPDATE FAILED!!!!!\n" + caught.getMessage());
                }
            });

            dialogBox.hide();
            newButton.setEnabled(true);
        }

    }

    private class MovementCreateHandler implements ClickHandler {
        TextBox description;
        DateBox date;
        DoubleBox amount;
        ListBox eu;
        DateBox until;

        public MovementCreateHandler(TextBox description, DateBox date, DoubleBox amount, ListBox eu, DateBox until) {
            this.description = description;
            this.date = date;
            this.amount = amount;
            this.eu = eu;
            this.until = until;
        }

        @Override
        public void onClick(ClickEvent event) {
            if (until == null) {
                createMovement(date);
            } else {
                if (until.getValue().before(date.getValue())) {
                    Window.alert("'Until' must be after 'Date'");
                } else {
                    int ind = 0;
                    while (date.getValue().before(until.getValue())) {
                        createMovement(date);
                        Date newDate = date.getValue();
                        CalendarUtil.addMonthsToDate(newDate, 1);
                        date.setValue(newDate);
                        ind++;
                    }
                    Window.alert("Added " + ind + " Duties.");
                }
            }

            dialogBox.hide();
            newButton.setEnabled(true);
        }

        public void createMovement(DateBox inputDate) {
            BankAccountMovementData movement = new BankAccountMovementData();
            movement.setDescription(description.getValue());
            movement.setDate(inputDate.getValue());
            movement.setAmount(amount.getValue());
            movement.setEu(eu.getValue(eu.getSelectedIndex()));
            movementController.insertMovement(movement, new AsyncCallback<BankAccountMovementData>() {
                @Override
                public void onSuccess(BankAccountMovementData result) {
                    // Window.alert("INSERTED!!!!!\n\nForce RELOAD.");
                    getRemoteMovementsByMonth(currentYear, currentMonth);
                }

                @Override
                public void onFailure(Throwable caught) {
                    caught.printStackTrace();
                    Window.alert("UPDATE FAILED!!!!!\n" + caught.getMessage());
                }
            });
        }

    }

}