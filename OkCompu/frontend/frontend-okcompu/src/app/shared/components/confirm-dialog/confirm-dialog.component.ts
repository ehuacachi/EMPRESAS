import { Component, Inject } from '@angular/core';
import { MaterialModule } from '../../../material/material.module';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ConfirmDialogData } from '../../../model/confirm-dialog-data';

@Component({
  selector: 'app-confirm-dialog',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './confirm-dialog.component.html',
  styleUrl: './confirm-dialog.component.css'
})
export class ConfirmDialogComponent {

  constructor(  
    public dialogRef: MatDialogRef<ConfirmDialogComponent>,  
    @Inject(MAT_DIALOG_DATA) public data: ConfirmDialogData  
  ) {}  

  onNoClick(): void {  
    this.dialogRef.close(false);  
  }  

  onYesClick(): void {  
    this.dialogRef.close(true);  
  }

}
