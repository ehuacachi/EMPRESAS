import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Marca } from '../../../model/marca';
import { MaterialModule } from '../../../material/material.module';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-marca-dialog',
  standalone: true,
  imports: [MaterialModule, FormsModule],
  templateUrl: './marca-dialog.component.html',
  styleUrl: './marca-dialog.component.css'
})
export class MarcaDialogComponent implements OnInit{

  marca: Marca;

  constructor(
    //Para recibir la data del otro compoente
    @Inject(MAT_DIALOG_DATA) private data: Marca,
    //Para obtener las propierdades del Dialog
    private _dialogRef: MatDialogRef<MarcaDialogComponent>
  ){}

  ngOnInit(): void {
    //spread operator : Cambiar a otra referencia
    this.marca = {...this.data};
    
    // this.marca = new Marca(),
    // this.marca.idMarca = this.data.idMarca;
    // this.marca.descripcion = this.data.descripcion;
    // this.marca.estado = this.data.estado;
    // this.marca.foto = this.data.foto;
  }

  operate(){

  }

  close(){
    this._dialogRef.close();
  }

}
