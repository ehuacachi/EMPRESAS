import { Component, Inject } from '@angular/core';
import { Igv } from '../../../model/igv';
import { MaterialModule } from '../../../material/material.module';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-igv-dialog',
  standalone: true,
  imports: [MaterialModule, FormsModule],
  templateUrl: './igv-dialog.component.html',
  styleUrl: './igv-dialog.component.css'
})
export class IgvDialogComponent {

  igv: Igv;

  constructor(
    //Para recibir la data del otro compoente
    @Inject(MAT_DIALOG_DATA) private data: Igv,
    //Para obtener las propierdades del Dialog
    private _dialogRef: MatDialogRef<IgvDialogComponent>
  ){}

  ngOnInit(): void {
    //spread operator : Cambiar a otra referencia
    this.igv = {...this.data};    
    
  }

  operate(){

  }

  close(){
    this._dialogRef.close();
  }

}
