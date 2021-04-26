import { HttpClient, HttpClientModule, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../common/user';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl ="http://localhost:8080/api/users";
  private cudUrl = "http://localhost:8080/cud/users";

  constructor(private httpClient : HttpClient) { }

  getUserList(page:number ,size:number) : Observable<GetResponseUser>{
    const searchUrl  = this.baseUrl+`?page=${page}&size=${size}`;
    console.log("searchUrl="+searchUrl);
    return this.httpClient.get<GetResponseUser>(searchUrl);
  }

  getSearchUserListMaxSalary(page:number,size:number,salary: number){
    const searchUrl = `${this.baseUrl}/search/findBySalaryLessThanEqual?salary=${salary}`
                 +`&page=${page}&size=${size}`;
    console.log(searchUrl)
    return this.httpClient.get<GetResponseUser>(searchUrl);
  }

  getSearchUserListMinSalary(page:number,size:number,salary: number){
    const searchUrl = `${this.baseUrl}/search/findBySalaryGreaterThanEqual?salary=${salary}`
                 +`&page=${page}&size=${size}`;
    console.log(searchUrl)
    return this.httpClient.get<GetResponseUser>(searchUrl);
  }

  getSearchUserListSalary(page:number,size:number,minSal: number,maxSal:number){
    const searchUrl = `${this.baseUrl}/search/findBySalaryBetween?salary_From=${minSal}&salary_To=${maxSal}`
                 +`&page=${page}&size=${size}`;
    console.log(searchUrl)
    return this.httpClient.get<GetResponseUser>(searchUrl);
  }

  
  updateUser(employeeId: string, value: any): Observable<Object> {
    return this.httpClient.patch(`${this.cudUrl}/update/${employeeId}`, value);
  }

  deleteUser(employeeId: string): Observable<any> {
    return this.httpClient.delete(`${this.cudUrl}/delete/${employeeId}`, { responseType: 'text' });
  }

  getUser(employeeId: string): Observable<GetUser> {
    return this.httpClient.get<GetUser>(`${this.baseUrl}/${employeeId}`);
  }
  createUser(user: object): Observable<object> {
    return this.httpClient.post(`${this.cudUrl}`+'/create', user);
  }
  
  saveUsersFromFile(file : File) : Observable<any>{
    const formdata: FormData = new FormData();
		formdata.append('file', file);
		const req = new HttpRequest('POST', `${this.cudUrl}/upload`, formdata, {
			  reportProgress: true,
			  responseType: 'text'
		});
	
		return this.httpClient.request(req);
  }
}

interface GetResponse{
  _embedded:{
    users: User[];
  }
}

interface GetResponseUser{
  _embedded:{
    users: User[];
  },
  page:{
    size:number,
    totalElements: number,
    totalPages: number,
    number: number
  }
}

interface GetUser{
  
    employeeId:string,
    login: string,
    name: string,
    salary: string
  
}



