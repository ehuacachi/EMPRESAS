import { Component, Inject, OnInit } from '@angular/core';
import { MaterialModule } from '../../../material/material.module';
import { FormsModule } from '@angular/forms';
import { Categoria } from '../../../model/categoria';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MarcaDialogComponent } from '../../marca/marca-dialog/marca-dialog.component';
import { CategoriaService } from '../../../services/categoria.service';
import { switchMap } from 'rxjs';

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
    private _dialogRef: MatDialogRef<MarcaDialogComponent>,
    private categoriaService: CategoriaService
  ){}

  ngOnInit(): void {
    //spread operator : Cambiar a otra referencia
    this.categoria = {...this.data};    
  }

  operate(){
    if (this.categoria != null && this.categoria.idCategoria > 0 ){
      //UPDATE
      this.categoriaService.update(this.categoria.idCategoria, this.categoria)
      .pipe(switchMap(() => this.categoriaService.findAll() ))
      .subscribe(data => {
        this.categoriaService.setCategoriaChange(data);
        this.categoriaService.setMessageChange("UPDATE!");
      });

    } else {
      //INSERT
      this.categoriaService.save(this.categoria)
      .pipe(switchMap(() => this.categoriaService.findAll() ))
      .subscribe(data => {
        this.categoriaService.setCategoriaChange(data);
        this.categoriaService.setMessageChange("CREATED!");
      });      
    }
    this.close();

  }

  close(){
    this._dialogRef.close();
  }

}
