
var baseURL ="http://localhost:8080/flatmates-project/";

function login() {
    loginForm.submit();
}

function register() {
    register.submit();
}

function delete2(){ //unsign user
    
    DeleteForm.submit();
}

function create(){ //create new flat
     CreateForm.submit();
}

function changePassword() { //change user password
   newPasswordUser.submit(); 
}

function newPaswordFlat() { //change flat passowrd
    newPasswordFlat.submit
}

function changeLaguage() {
    changeLanguage.submit();
}

function signToFlat(){
    Sign.submit();
}

function logout(){
    window.location.replace(baseURL + "MainList.jsp?logout=logout");
}


