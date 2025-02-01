import { Component, Inject, OnInit } from '@angular/core';
import { UnidadMedida } from '../../../model/unidad-medida';
import { MaterialModule } from '../../../material/material.module';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { UnidadMedidaService } from '../../../services/unidad-medida.service';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-unidad-medida-dialog',
  standalone: true,
  //Two-Way Binding: Se necesita UTILIZAR el FormsModule y el ngModel (html)
  imports: [MaterialModule, FormsModule],
  templateUrl: './unidad-medida-dialog.component.html',
  styleUrl: './unidad-medida-dialog.component.css'
})
export class UnidadMedidaDialogComponent implements OnInit{

  //variable para utilizar con el ngModel.
  unidadMedida: UnidadMedida;

  constructor(
    //Para recibir la data del otro compoente
    @Inject(MAT_DIALOG_DATA) private data: UnidadMedida,
    //Para obtener las propierdades del Dialog
    private _dialogRef: MatDialogRef<UnidadMedidaDialogComponent>,
    private unidadMedidaService: UnidadMedidaService
  ){}

  ngOnInit(): void {
    //spread operator : Cambiar a otra referencia
    this.unidadMedida = {...this.data};
    
    // this.marca = new Marca(),
    // this.marca.idMarca = this.data.idMarca;
    // this.marca.descripcion = this.data.descripcion;
    // this.marca.estado = this.data.estado;
    // this.marca.foto = this.data.foto;
  }

  operate(){
    if (this.unidadMedida != null && this.unidadMedida.idUnidadMedida > 0 ){
      //UPDATE
      this.unidadMedidaService.update(this.unidadMedida.idUnidadMedida, this.unidadMedida)
      .pipe(switchMap(() => this.unidadMedidaService.findAll() ))
      .subscribe(data => {
        this.unidadMedidaService.setUnidadMedidaChange(data);
        this.unidadMedidaService.setMessageChange("UPDATE!");
      });

    } else {
      //INSERT
      this.unidadMedidaService.save(this.unidadMedida)
      .pipe(switchMap(() => this.unidadMedidaService.findAll() ))
      .subscribe(data => {
        this.unidadMedidaService.setUnidadMedidaChange(data);
        this.unidadMedidaService.setMessageChange("CREATED!");
      });      
    }
    this.close();

  }

  close(){
    this._dialogRef.close();
  }

}
