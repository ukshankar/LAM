import { Component } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { RouterOutlet } from '@angular/router';
import {MatTableModule} from '@angular/material/table';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormControl, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import { MatButton, MatButtonModule } from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import { CommonModule } from '@angular/common';  


export interface AIAction {
  actionName: string;
  actionType: string;
  description: string;
}
const BASE_URL="http://localhost:8080/api/";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, MatCardModule, FormsModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule,MatButtonModule, RouterOutlet, FlexLayoutModule, MatTableModule, HttpClientModule, FormsModule, MatFormFieldModule, MatInputModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'ui';
  displayedColumns: string[] = ['actionName'];
  dataSource : any;
  actionSource : any = [];
  actionColumns: string[] = ['actionName'];


  promptResult: any | undefined = undefined;

  inputForm = new FormControl('Hey new employee joined today his name is Shahruh Devgan, his id is 788778', [Validators.required]);

  constructor(private httpClient: HttpClient){}
  ngOnInit() {
      this.httpClient.get(BASE_URL + "actions").subscribe (a =>{
        console.log(a);
        this.dataSource = a;
      });
  }

  sendPrompt(){
    this.httpClient.post(BASE_URL + "prompt", JSON.parse(  "{\"prompt\": \""+ this.inputForm.value +"\"}"    )).subscribe(a=>{
      this.promptResult = a;
    });
  }
  executeAction(){
    this.httpClient.post(BASE_URL + "execute", JSON.parse(  "{\"prompt\": \""+ this.promptResult.prompt +"\"}")).subscribe((a:any)=>{
      console.log(a);
      this.actionSource.push(a);
      this.actionSource = [...this.actionSource];  
      this.promptResult = undefined;
    });
  }
}
