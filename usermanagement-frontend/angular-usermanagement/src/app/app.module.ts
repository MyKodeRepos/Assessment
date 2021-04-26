import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import { AppComponent } from './app.component';
import { UsersListComponent } from './components/users-list/users-list.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap'
import { RouterModule, Routes } from '@angular/router';
import { UserService } from './services/user.service';
import { SearchComponent } from './components/search/search.component';
import { FormsModule , ReactiveFormsModule} from '@angular/forms';
import {DataTablesModule} from 'angular-datatables';
import { UploadusersComponent } from './components/uploadusers/uploadusers.component';
import {MDBBootstrapModule } from  'angular-bootstrap-md'         


const routes: Routes =[
  {path:'search/salary/:minSal/:maxSal' , component:UsersListComponent},
  {path:'search/min/:minSalary' , component:UsersListComponent},
  {path:'search/max/:maxSalary' , component:UsersListComponent},
  {path:'users' , component:UsersListComponent},
  {path:'upload' , component:UploadusersComponent},
  {path:'',redirectTo:'/users' , pathMatch: 'full'},
  {path:'**', redirectTo: '/users', pathMatch: 'full'}
];

@NgModule({
  declarations: [
    AppComponent,
    UsersListComponent,
    SearchComponent,
    UploadusersComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    DataTablesModule,
    MDBBootstrapModule.forRoot(),
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
