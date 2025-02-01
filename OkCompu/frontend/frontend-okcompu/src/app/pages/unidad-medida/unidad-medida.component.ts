import { Component, OnInit, ViewChild } from '@angular/core';
import { MaterialModule } from '../../material/material.module';
import { MatTable, MatTableDataSource } from '@angular/material/table';
import { UnidadMedida } from '../../model/unidad-medida';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { UnidadMedidaService } from '../../services/unidad-medida.service';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UnidadMedidaDialogComponent } from './unidad-medida-dialog/unidad-medida-dialog.component';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-unidad-medida',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './unidad-medida.component.html',
  styleUrl: './unidad-medida.component.css'
})
export class UnidadMedidaComponent implements OnInit {

  dataSource: MatTableDataSource<UnidadMedida>;
  
  columnDefinitions = [
    { def: 'idUnidadMedida', label: 'ID', hide: true},
    { def: 'descripcion', label: 'Descripcion', hide: false},
    { def: 'abreviatura', label: 'Abreviatura', hide: false},
    { def: 'estado', label: 'Estado', hide: false},
    { def: 'acccion', label: 'Acccion', hide: false}
  ]  

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private unidadMedidaService: UnidadMedidaService,
    private _dialog: MatDialog,
    private _snackBar: MatSnackBar
    
  ){}

  ngOnInit(): void {
    this.unidadMedidaService.findAll().subscribe(data => {
      this.createTable(data);
    });
    
    this.unidadMedidaService.getUnidadMedidaChange().subscribe(data => {
      this.createTable(data);
    });

    this.unidadMedidaService.getMessageChange().subscribe(data => {
      this._snackBar.open(data, 'INFO', {duration: 2000, horizontalPosition: 'right', verticalPosition: 'top'});
    });
  }

  createTable(data: UnidadMedida[]){
    this.dataSource = new MatTableDataSource(data);    
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  applyFilter(e : any){
    this.dataSource.filter = e.target.value.trim();
  }

  getDisplayedColumns() {
    return this.columnDefinitions.filter(cd => !cd.hide).map(cd => cd.def);
  }

  // El Singon ? : Valor opcional, este valor siempre debe ir al ultimo.
  openDialog(unidadMedida?: UnidadMedida){    
    this._dialog.open(UnidadMedidaDialogComponent, {
      width: '350px',
      data: unidadMedida,      
      //TRUE: para NO cerrar con la tacla ESC
      disableClose: false,
    })
  }

  delete(idUnidadPerdida: number){
    this.unidadMedidaService.delete(idUnidadPerdida)
    .pipe(switchMap(() => this.unidadMedidaService.findAll() ))
    .subscribe(data => {
      this.unidadMedidaService.setUnidadMedidaChange(data);
      this.unidadMedidaService.setMessageChange("DELETED!");
    });
  }


}
