import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {
  }

  doSearchMin(minSalary:number){
    console.log(`salary=${minSalary}`);
    this.router.navigateByUrl(`/search/min/${minSalary}`);
  }
  doSearchMax(maxSalary:number){
    console.log(`salary=${maxSalary}`);
    this.router.navigateByUrl(`/search/max/${maxSalary}`);
  }

  doSearch(minSal:number , maxSal:number){
    console.log(`minSalary=${minSal} maxSalary=${maxSal}`);
    this.router.navigateByUrl(`/search/salary/${minSal}/${maxSal}`);
  }
}
