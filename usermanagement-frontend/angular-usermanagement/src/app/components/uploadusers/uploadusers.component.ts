import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';  
import { ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-uploadusers',
  templateUrl: './uploadusers.component.html',
  styleUrls: ['./uploadusers.component.css']
})
export class UploadusersComponent implements OnInit {

  constructor(private userService : UserService, private route:ActivatedRoute) { }

  submitted=false;
  uploadedMessage = false;
  errorMessage=false;
  ngOnInit(): void {
    this.uploadedMessage = false;
    this.errorMessage = false;
  }

  uploadedFiles :FileList

  currentFile : File;

  uploadUsersform=new FormGroup({  
    usersFile:new FormControl()  
  });

  selectFile(event:any) {  
    const file :File = event.target.files.item(0);  
    this.currentFile =file;
    this.uploadedMessage =false;
    this.errorMessage = false;
    console.log("file.type"+file.name);
   
      var size = event.target.files[0].size;  
      if(size > 1000000)  
      {  
          alert("size must not exceeds 1 MB"); 
          const filevalue:any = this.uploadUsersform.get('usersFile')??'';
          if(filevalue!=null){
          filevalue.setValue("");  
          }
      }  
      else  
      {  
        this.uploadedFiles = event.target.files;  
      }  
    } 
  

  uploadUsersForm(){
    this.userService.saveUsersFromFile(this.currentFile).subscribe(  
      data => {  
        console.log(data);  
        this.uploadedMessage=true;  
      },  
      error => {
        console.log(error); 
        this.errorMessage = true;
        this.uploadedMessage = false;
      });
  }
}



