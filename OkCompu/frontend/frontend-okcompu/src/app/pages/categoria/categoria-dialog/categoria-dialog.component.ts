import { Component, Inject, OnInit } from '@angular/core';
import { MaterialModule } from '../../../material/material.module';
import { FormsModule } from '@angular/forms';
import { Categoria } from '../../../model/categoria';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MarcaDialogComponent } from '../../marca/marca-dialog/marca-dialog.component';

@Component({
  selector: 'app-categoria-dialog',
  standalone: true,
  imports: [MaterialModule, FormsModule],
  templateUrl: './categoria-dialog.component.html',
  styleUrl: './categoria-dialog.component.css'
})
export class CategoriaDialogComponent implements OnInit {

  categoria: Categoria;

  constructor(    
    @Inject(MAT_DIALOG_DATA) private data: Categoria,
    //Para obtener las propierdades del Dialog
    private _dialogRef: MatDialogRef<MarcaDialogComponent>
  ){}

  ngOnInit(): void {
    //spread operator : Cambiar a otra referencia
    this.categoria = {...this.data};    
  }

  operate(){

  }

  close(){
    this._dialogRef.close();
  }

}
