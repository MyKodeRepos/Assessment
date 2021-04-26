import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/common/user';
import { UserService } from 'src/app/services/user.service';
import {FormControl,FormGroup,Validators} from '@angular/forms';  

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent implements OnInit {

  thePageNumber: number = 1;
  thePageSize:number = 10;
  theTotalElements:number = 0;

  searchModeMin :boolean = false;
  searchModeMax :boolean = false;
  searchModeSalary:boolean = false;
  previousMinSal:number=0.00;
  previousMaxSal :number=0.00;
  constructor(private userService : UserService, private route:ActivatedRoute) { }
  users : any;
  dtOptions: DataTables.Settings = {};  
  dtTrigger: User= new User();  
  user : User=new User();  
  deleteMessage=false;  
  isupdated = false;
  isSaved = false;   
  curUpdatedEmpId = '';
  usersList : User = new User();
  headElements = ['Employee Id', 'Employee Login', 'Employee Name', 'Salary'];
  ngOnInit(): void {
    this.isupdated=false; 
    this.isSaved = false; 
    this.route.paramMap.subscribe(()=>{
      this.listOfUsers();
    });
  }

  listOfUsers(){
    this.searchModeMin = this.route.snapshot.paramMap.has('minSalary');
    this.searchModeMax = this.route.snapshot.paramMap.has('maxSalary'); 
    this.searchModeSalary= this.route.snapshot.paramMap.has('minSal');  
    console.log("searchModeMin"+this.searchModeMin+"searchModeMax"+this.searchModeMax) ;            
    if(this.searchModeMin){
      this.getSearchUserListsMinSalary();
    }else if(this.searchModeMax){
      this.getSearchUserListsMaxSalary();
    }else if(this.searchModeSalary){
      this.getSearchUserListsSalary();
    }else{
      this.getUserList();
    }
  }

  processResult(){
    return (data: { _embedded: { users: User[]; }; page: { number: number; size: number; totalElements: number; }; })=> {
      this.users = data._embedded.users;
      this.thePageNumber = data.page.number+1;
      this.thePageSize = data.page.size;
      this.theTotalElements = data.page.totalElements;
    };
  } 

  getUserList(){
    console.log("listOfUsers");
    this.userService.getUserList(this.thePageNumber - 1 , this.thePageSize)
    .subscribe( this.processResult());
  }

  getSearchUserListsMinSalary(){
    const theSal : number = +(this.route.snapshot.paramMap.get('minSalary')?? '');
    if(this.previousMinSal != theSal ){
      this.thePageNumber = 1;
    }
    this.previousMinSal = theSal;

    this.userService.getSearchUserListMinSalary(this.thePageNumber - 1 , this.thePageSize , theSal)
    .subscribe( this.processResult());
  }

  getSearchUserListsMaxSalary(){
    const theSal : number = +(this.route.snapshot.paramMap.get('maxSalary')?? '');
    if(this.previousMaxSal != theSal ){
      this.thePageNumber = 1;
    }
    this.previousMaxSal = theSal;

    this.userService.getSearchUserListMaxSalary(this.thePageNumber - 1 , this.thePageSize , theSal)
    .subscribe( this.processResult());
  }

  getSearchUserListsSalary(){
    const minSal : number = +(this.route.snapshot.paramMap.get('minSal')?? '');
    const maxSal : number = +(this.route.snapshot.paramMap.get('maxSal')?? '');
    if(this.previousMaxSal != maxSal || this.previousMinSal!= minSal){
      this.thePageNumber = 1;
    }
    this.previousMinSal = minSal;
    this.previousMaxSal = maxSal;

    this.userService.getSearchUserListSalary(this.thePageNumber - 1 , this.thePageSize , minSal, maxSal)
    .subscribe( this.processResult());
  }

  updatePageSize(pageSize:number){
    this.thePageSize  = pageSize;
    this.thePageNumber = 1;
    this.listOfUsers();
  }

  updateUser(id: string){ 
    this.curUpdatedEmpId = id;
    this.userService.getUser(id)  
      .subscribe(  
        data => {  
          this.usersList=data   
          console.log("usersList"+this.usersList.employeeId)          
        },  
        error => console.log(error));  
  }  

  userupdateform=new FormGroup({  
    employeeId:new FormControl(),  
    login:new FormControl(),  
    name:new FormControl(),  
    salary:new FormControl()  
  });
  
  saveUserform=new FormGroup({  
    employeeId:new FormControl(),  
    login:new FormControl(),
    name:new FormControl(),  
    salary:new FormControl()  
  });  

  saveUserForm(){  
    this.user=new User();     
    this.user.employeeId=this.EmployeeIdSave.value;  
    this.user.login=this.LoginSave.value;  
    this.user.name=this.NameSave.value; 
    this.user.salary=this.SalarySave.value;  
    this.isSaved = true;  
    this.saveNewUser();  
  }  

  saveNewUser() {  
    this.userService.createUser(this.user)  
      .subscribe(data => {
        console.log(data)
        this.isSaved = true;
        this.getUserList();
      }
      , error => console.log(error));  
    this.user = new User();  
  }  
  deleteUser(employeeId: string) {  
    this.userService.deleteUser(employeeId)  
      .subscribe(  
        data => {  
          console.log(data);  
          this.deleteMessage=true;  
          this.getUserList();
        },  
        error => console.log(error));  
  }  

  updateUserForm(upduser:any){  
    this.user=new User();   
   this.user.employeeId=this.EmployeeId.value;  
   this.user.login=this.Login.value;  
   this.user.name=this.Name.value;  
   this.user.salary=this.Salary.value;  
   console.log(this.Salary.value);  
     
  
   this.userService.updateUser(this.user.employeeId,this.user).subscribe(  
    data => {       
      this.isupdated=true;  
      this.getUserList() ;
    },  
    error => console.log(error));  
  } 

  get EmployeeId(){  
    const empId:any = this.userupdateform.get('employeeId')??'';
    return empId;
  }  
  
  get Login(){  
    const login:any = this.userupdateform.get('login')??'';
    return login;  
  }  
  
  get Name(){  
    const name:any = this.userupdateform.get('name')??'';
    return name;  
  }  
  
  get Salary(){  
    const salary:any = this.userupdateform.get('salary')??'';
    return salary;  
  }  

  get EmployeeIdSave(){  
    const empId:any = this.saveUserform.get('employeeId')??'';
    return empId;
  }  
  
  get LoginSave(){  
    const login:any = this.saveUserform.get('login')??'';
    return login;  
  }  
  
  get NameSave(){  
    const name:any = this.saveUserform.get('name')??'';
    return name;  
  }  
  
  get SalarySave(){  
    const salary:any = this.saveUserform.get('salary')??'';
    return salary;  
  }  
  
  changeisUpdate(){  
    this.isupdated=false;  
  } 

  changeisSaved(){
    this.isSaved=false;
  }
}
