import { Component, Inject } from '@angular/core';
import { MaterialModule } from '../../../material/material.module';
import { FormsModule } from '@angular/forms';
import { TipoDocumento } from '../../../model/tipo-documento';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-tipo-documento-dialog',
  standalone: true,
  imports: [MaterialModule, FormsModule],
  templateUrl: './tipo-documento-dialog.component.html',
  styleUrl: './tipo-documento-dialog.component.css'
})
export class TipoDocumentoDialogComponent {

  tipoDocumento: TipoDocumento;

  constructor(
    //Para recibir la data del otro compoente
    @Inject(MAT_DIALOG_DATA) private data: TipoDocumento,
    //Para obtener las propierdades del Dialog
    private _dialogRef: MatDialogRef<TipoDocumentoDialogComponent>
  ){}

  ngOnInit(): void {
    //spread operator : Cambiar a otra referencia
    this.tipoDocumento = {...this.data};   
    
  }

  operate(){

  }

  close(){
    this._dialogRef.close();
  }

}
