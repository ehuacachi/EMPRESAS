import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Marca } from '../../../model/marca';
import { MaterialModule } from '../../../material/material.module';
import { FormsModule } from '@angular/forms';
import { MarcaService } from '../../../services/marca.service';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-marca-dialog',
  standalone: true,
  //Two-Way Binding: Se necesita UTILIZAR el FormsModule y el ngModel (html)
  imports: [MaterialModule, FormsModule],
  templateUrl: './marca-dialog.component.html',
  styleUrl: './marca-dialog.component.css'
})
export class MarcaDialogComponent implements OnInit{
  //variable para utilizar con el ngModel.
  marca: Marca;

  constructor(
    //Para recibir la data del otro compoente
    @Inject(MAT_DIALOG_DATA) private data: Marca,
    //Para obtener las propierdades del Dialog
    private _dialogRef: MatDialogRef<MarcaDialogComponent>,
    private marcaService: MarcaService
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
    if (this.marca != null && this.marca.idMarca > 0 ){
      //UPDATE
      this.marcaService.update(this.marca.idMarca, this.marca)
      .pipe(switchMap(() => this.marcaService.findAll() ))
      .subscribe(data => {
        this.marcaService.setMarcaChange(data);
        this.marcaService.setMessageChange("UPDATE!");
      });

    } else {
      //INSERT
      this.marcaService.save(this.marca)
      .pipe(switchMap(() => this.marcaService.findAll() ))
      .subscribe(data => {
        this.marcaService.setMarcaChange(data);
        this.marcaService.setMessageChange("CREATED!");
      });      
    }
    this.close();

  }

  close(){
    this._dialogRef.close();
  }

}
