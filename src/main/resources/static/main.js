let getAllUsers = "http://localhost:8088/api/users"

function Role (roleName) {
  this.id = roleName === "USER" ? 1 : 2;
  this.roleName = roleName;

}


function rolesAsString(result) {
  let roles = " ";
  for (let role in result.roleList) {
    roles += result.roleList[role].roleName + " ";
  }
  return roles;
}

function User(firstName, lastName, age, email, pass, roles) {
  this.firstName = firstName;
  this.lastName = lastName;
  this.age = age;
  this.pass = pass;
  this.email = email;
  this.roleList = roles;
}

function fillTableRow(user) {
  let row = document.createElement('tr');
  row.innerHTML = "<td>"+user.id+"</td>"
      + "<td>"+user.firstName +"</td>"
      + "<td>"+user.lastName+"</td>"
      + "<td>"+user.email+"</td>"
      +"<td>"+user.age+"</td>"
      + "<td>"+ rolesAsString(user) +"</td>"
  return row;
}

async function postRequest(user) {
  await fetch(getAllUsers, {
    method:'POST',
    body: JSON.stringify(user),
    headers: {"content-type": "application/json"}
  });
}

async function deleteRequest(id) {
  await fetch(getAllUsers + "/" + id, {method: 'DELETE'})
}

async function patchRequest(user) {
  await fetch(getAllUsers, {
    method:'PATCH',
    body: JSON.stringify(user),
    headers: {"content-type": "application/json"}
  });
}

async function getPrincipalInfo() {
  let url = "http://localhost:8088/api/user";
  let response = await fetch(url);
  let principal = await response.json(); // читаем ответ в формате JSON
  let navbarText = document.createElement('h4');
  navbarText.className="text-white";
  navbarText.innerHTML = principal.email + " with roles:" + rolesAsString(principal);
  document.getElementById("navbar-text").append(navbarText);
  return principal;
}

function fillForm(user, formName) {
  document.getElementById(`id-${formName}`).value = user.id;
  document.getElementById(`name-${formName}`).value = user.firstName;
  document.getElementById(`last-name-${formName}`).value = user.lastName;
  document.getElementById(`email-${formName}`).value = user.email;
  document.getElementById(`age-${formName}`).value = user.age;
  document.getElementById(`password-${formName}`).value = '';
}

function readForm(formName) {
  let firstName = document.getElementById(`name-${formName}`).value;
  let lastName = document.getElementById(`last-name-${formName}`).value;
  let email = document.getElementById(`email-${formName}`).value;
  let age = document.getElementById(`age-${formName}`).value;
  let pass = document.getElementById(`password-${formName}`).value;
  let roles = Array.from(document.getElementById(`role-${formName}`)).filter(el=>el.selected).map(el => new Role(el.value));
  return new User(firstName,lastName,age,email,pass, roles);
}

async function fillTable() {
  let allUsersResponse = await fetch(getAllUsers);
  let allUsersResult = await allUsersResponse.json();
  allUsersResult.forEach(user => {
    let row = fillTableRow(user);
    row.innerHTML += `<td><button id = delete-button${user.id} type=button class='btn btn-danger' data-toggle=modal data-target='#deleteModal'>Delete</button></td>`;
    row.innerHTML += `<td><button id = edit-button${user.id} type=button class='btn btn-info' data-toggle=modal data-target='#editModal'>Edit</button></td>`;
    document.getElementById("admin").querySelector("tbody").append(row);
    document.getElementById(`delete-button${user.id}`).addEventListener('click', ()=>fillForm(user, "delete"))
    document.getElementById(`edit-button${user.id}`).addEventListener('click', ()=>fillForm(user, "edit"))
  })

}


async function main() {
  let principal = await getPrincipalInfo();
  let row = fillTableRow(principal);
  document.getElementById("user").querySelector("tbody").append(row);

  await fillTable();

  document.getElementById("edit-submit").addEventListener('click', ()=>{
    let user = readForm('edit')
    user.id = document.getElementById("id-edit").value;

    document.getElementById("admin").querySelector("tbody").innerHTML='';
    patchRequest(user).then(fillTable);
  })

  document.getElementById("delete-submit").addEventListener('click', ()=>{
    deleteRequest(document.getElementById("id-delete").value).then(fillTable);
    document.getElementById("admin").querySelector("tbody").innerHTML='';
  })

  //заполнение формы нового юзера
  document.getElementById('new-user-form').addEventListener('submit', (evt)=> {
    evt.preventDefault();
    let user = readForm('new');
    document.getElementById("admin").querySelector("tbody").innerHTML='';
    postRequest(user).then(fillTable);
    document.getElementById("new-user-form").reset();
  })

}

main();




