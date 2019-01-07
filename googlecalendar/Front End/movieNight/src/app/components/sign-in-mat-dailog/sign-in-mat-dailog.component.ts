import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
export interface DialogData {
   userEmail: string;
}
@Component({
  selector: 'app-sign-in-mat-dailog',
  templateUrl: './sign-in-mat-dailog.component.html',
  styleUrls: ['./sign-in-mat-dailog.component.css']
})
export class SignInMatDailogComponent implements OnInit {

  
  constructor(
    public dialogRef: MatDialogRef<SignInMatDailogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

  onNoClick(): void {
    this.dialogRef.close();
  }


  ngOnInit() {
  }

}
