import { Component, OnInit, ViewChild } from '@angular/core';
import { MaterialModule } from '../../material/material.module';
import { RouterOutlet } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { TipoDocumento } from '../../model/tipo-documento';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { TipoDocumentoService } from '../../services/tipo-documento.service';
import { switchMap } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { TipoDocumentoDialogComponent } from './tipo-documento-dialog/tipo-documento-dialog.component';

@Component({
  selector: 'app-tipo-documento',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './tipo-documento.component.html',
  styleUrl: './tipo-documento.component.css'
})
export class TipoDocumentoComponent implements OnInit{

  dataSource: MatTableDataSource<TipoDocumento>;
  
  columnDefinitions = [
    { def: 'idTipoDocumento', label: 'idTipoDocumento', hide: true},
    { def: 'descripcion', label: 'descripcion', hide: false},
    { def: 'abreviatura', label: 'abreviatura', hide: false},    
    { def: 'acccion', label: 'acccion', hide: false}
  ]
  //displayedColumns: string[] = [ 'idPatient', 'firstName', 'lastName', 'dni', 'actions'];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private tipoDocumentoService: TipoDocumentoService,
    private _dialog: MatDialog
  ){}

  ngOnInit(): void {
    this.tipoDocumentoService.findAll().subscribe(data => {
      this.createTable(data);
    });    
  }

  createTable(data: TipoDocumento[]){
    this.dataSource = new MatTableDataSource(data);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  applyFilter(e : any){
    this.dataSource.filter = e.target.value.trim();
  }

  getDisplayedColumns(){
    return this.columnDefinitions.filter(cd => !cd.hide).map(cd => cd.def);
  }  
  
  openDialog(tipoDocumento?: TipoDocumento){    
    this._dialog.open(TipoDocumentoDialogComponent, {
      width: '350px',
      data: tipoDocumento,      
      //TRUE: para NO cerrar con la tacla ESC
      disableClose: false,
    })
  }


}
