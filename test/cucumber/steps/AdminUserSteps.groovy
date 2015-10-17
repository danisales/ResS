package steps

import pages.AdminUserSignUpPage
import user.AdminUser
import static cucumber.api.groovy.EN.*

/*
Controller

Given the system has no user with the CPF "123.456.789-12"
And has no user with the login "admin"
When I register a user called "John Doe", with CPF "123.456.789-12", login "admin", password "abcdef", email "johndoe@johndoe.com" and phone "0000-0000"
Then the user "admin" is stored in the system
*/

Given(~'^The system has no user with the CPF "[^"*]"$'){String cpf->
    userCpf = AdminUser.findByAdminCpf(cpf)
    assert userCpf == null
}
And(~'^has no user with the login "[^"*]"$'){String login->
    userLogin = AdminUser.findByAdminLogin(login)
    assert userLogin == null
}
When(~'^I register a user called "[^"*]", with CPF "[^"*]", login "[^"*]", password "[^"*]", email "[^"*]" and phone "[^"*]" $'){
    String name, String cpf, String login, String password, String email, String phone ->
        AdminUserTestDataAndOperations.createUser(name, cpf, login, password, email, phone)
}
Then(~'^The user "[^"*]" is stored in the system$'){ String login ->
    userLogin = AdminUser.findByAdminLogin(login)
    assert userLogin != null
}

/*
GUI

Given I’m at the sign up page
When I ask the system to add a user called "John Doe", with CPF "123.456.789-12", login "admin", password "abcdef", email "johndoe@johndoe.com" and phone "0000-0000"
Then I see a confirmation message
*/

Given(~'^I’m at the sign up page$'){->
    to AdminUserSignUpPage
    at AdminUserSignUpPage
}
When(~'^When I ask the system to add a user called"[^"*]", with CPF "[^"*]", login "[^"*]", password "[^"*]", email "[^"*]" and phone "[^"*]" $'){
    String name, String cpf, String login, String password, String email, String phone ->
        page.fillUserInfo(name, cpf, login, password, email, phone)
        page.submitUserInfo()
}
Then(~'^I see a confirmation message$'){->
    assert page.hasMessage()
}