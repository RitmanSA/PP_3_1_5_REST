let getOneUser =  "http://localhost:8088/api/user"
let getAllUsers = "http://localhost:8088/api/users"

function rolesAsString(result) {
  let roles = " ";
  for (let role in result.roleList) {
    roles += result.roleList[role].roleName + " ";
  }
  return roles;
}


async function navbarText() {
  let response = await fetch(getOneUser);
  let result = await response.json(); // читаем ответ в формате JSON
  let navbar_text = document.createElement('h4');
  navbar_text.className="text-white";
  navbar_text.innerHTML = result.email + " with roles:" + rolesAsString(result);
  document.getElementById("navbar-text");
  document.getElementById("navbar-text").append(navbar_text);


  let row = document.createElement('tr');
  row.innerHTML = "<td>"+result.id+"</td>"
      + "<td>"+result.firstName +"</td>"
      + "<td>"+result.lastName+"</td>"
      + "<td>"+result.email+"</td>"
      + "<td>"+ rolesAsString(result) +"</td>"
  document.getElementById("user").querySelector("tbody").append(row);



  let allUsersResponse = await fetch(getAllUsers);
  let allUsersResult = await allUsersResponse.json();
  allUsersResult.forEach(user => {
    let row = document.createElement('tr');
    row.innerHTML = "<td>"+user.id+"</td>"
        + "<td>"+user.firstName +"</td>"
        + "<td>"+user.lastName+"</td>"
        + "<td>"+user.email+"</td>"
        + "<td>"+ rolesAsString(user) +"</td>"
    document.getElementById("admin").querySelector("tbody").append(row);
  })
}

navbarText();




