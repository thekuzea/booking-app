export class Profile {

  username: string;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  confirmPassword: string;
  phoneNumber: string;
  role: string;

  constructor() {
    this.username = "";
    this.firstName = "";
    this.lastName = "";
    this.email = "";
    this.password = "";
    this.confirmPassword = "";
    this.phoneNumber = "";
    this.role = "";
  }
}
