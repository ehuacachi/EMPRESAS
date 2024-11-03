import { Component, OnInit, ViewChild } from '@angular/core';
import { Marca } from '../../model/marca';
import { MatTableDataSource } from '@angular/material/table';
import { MarcaService } from '../../services/marca.service';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MaterialModule } from '../../material/material.module';
import { MarcaDialogComponent } from './marca-dialog/marca-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-marca',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './marca.component.html',
  styleUrl: './marca.component.css'
})
export class MarcaComponent implements OnInit{

  dataSource: MatTableDataSource<Marca>;
  
  columnDefinitions = [
    { def: 'idMarca', label: 'ID', hide: true},
    { def: 'descripcion', label: 'Descripcion', hide: false},
    { def: 'foto', label: 'Abreviatura', hide: false},
    { def: 'estado', label: 'Estado', hide: false},
    { def: 'acccion', label: 'Acccion', hide: false}
  ]
  //displayedColumns: string[] = [ 'idPatient', 'firstName', 'lastName', 'dni', 'actions'];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private marcaService: MarcaService,
    private _dialog: MatDialog
    
  ){}

  ngOnInit(): void {
    this.marcaService.findAll().subscribe(data => {
      this.createTable(data);
    })      
  }

  createTable(data: Marca[]){
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
  openDialog(marca?: Marca){    
    this._dialog.open(MarcaDialogComponent, {
      width: '350px',
      data: marca,      
      //TRUE: para NO cerrar con la tacla ESC
      disableClose: false,
    })
  }


}
