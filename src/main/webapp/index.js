const uri = "http://localhost:8080/customer";
let customers = [];
let updateIndex = 0;

function getItems() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
        _displayItems(JSON.parse(xhttp.responseText));
    }
  };
  xhttp.open("GET", uri, true);
  xhttp.send();
}

function addItem() {
  const addNameTextbox = document.getElementById("customername");

  const item = { customername: document.getElementById("customername").value };

  var xhttp = new XMLHttpRequest();
  xhttp.open("POST", uri, true);
  xhttp.setRequestHeader("Content-type", "application/json");
  xhttp.send(JSON.stringify(item));
  alert("inserted successfully...");
  getItems() 
}

function deleteItem(id) {
  const item = {
    customerid: id,
    customername: document.getElementById("customername").value.trim(),
  };
  var xhttp = new XMLHttpRequest();
  xhttp.open("DELETE", uri, true);
  xhttp.setRequestHeader("Content-type", "application/json");
  xhttp.send(JSON.stringify(item));
  getItems();
}

function editItem(id) {
  document.getElementById("myBtn").innerHTML = "Update";
  const item = customers.find((item) => item.customerid === id);
  document.getElementById("customername").value = item.customername;
  updateIndex = id;
}

function saveORupdateItem() {
  //   document.getElementById("myBtn").innerHTML == "Save"
  //     ? addItem()
  //     : updateItem();
  if (document.getElementById("myBtn").innerHTML == "Save") {
    addItem();
  } else {
    updateItem();
  }
}

function updateItem() {
  const item = {
    customerid: updateIndex,
    customername: document.getElementById("customername").value.trim(),
  };
  var xhttp = new XMLHttpRequest();
  xhttp.open("PUT", uri, true);
  xhttp.setRequestHeader("Content-type", "application/json");
  xhttp.send(JSON.stringify(item));

  document.getElementById("myBtn").innerHTML = "Save";
  document.getElementById("customername").value = "";
  updateIndex = 0;
  getItems();
}

function _displayItems(data) {
  const tBody = document.getElementById("customers");
  tBody.innerHTML = "";

  const button = document.createElement("button");

  data.forEach((item) => {
    let editButton = button.cloneNode(false);
    editButton.innerText = "Edit";
    editButton.setAttribute("onclick", `editItem(${item.customerid})`);

    let deleteButton = button.cloneNode(false);
    deleteButton.innerText = "Delete";
    deleteButton.setAttribute("onclick", `deleteItem(${item.customerid})`);

    let tr = tBody.insertRow();

    let td1 = tr.insertCell(0);
    let custid = document.createTextNode(item.customerid);
    td1.appendChild(custid);

    let td2 = tr.insertCell(1);
    let custname = document.createTextNode(item.customername);
    td2.appendChild(custname);

    let td3 = tr.insertCell(2);
    td3.appendChild(editButton);

    let td4 = tr.insertCell(3);
    td4.appendChild(deleteButton);
  });

  customers = data;
}