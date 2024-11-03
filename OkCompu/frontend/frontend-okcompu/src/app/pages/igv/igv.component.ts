import { Component, ViewChild } from '@angular/core';
import { MaterialModule } from '../../material/material.module';
import { MatTableDataSource } from '@angular/material/table';
import { Igv } from '../../model/igv';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { IgvService } from '../../services/igv.service';
import { MatDialog } from '@angular/material/dialog';
import { IgvDialogComponent } from './igv-dialog/igv-dialog.component';

@Component({
  selector: 'app-igv',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './igv.component.html',
  styleUrl: './igv.component.css'
})
export class IgvComponent {

  dataSource: MatTableDataSource<Igv>;
  
  columnDefinitions = [
    { def: 'idIgv', label: 'ID', hide: true},
    { def: 'actividad', label: 'Actividad', hide: false},
    { def: 'valor', label: 'Valor', hide: false},    
    { def: 'igvFecha', label: 'Fecha', hide: false},
    { def: 'estado', label: 'Estado', hide: false},
    { def: 'acccion', label: 'Acccion', hide: false}
  ]
  //displayedColumns: string[] = [ 'idPatient', 'firstName', 'lastName', 'dni', 'actions'];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private igvService: IgvService,
    private _dialog: MatDialog
    
  ){}

  ngOnInit(): void {
    this.igvService.findAll().subscribe(data => {
      this.createTable(data);
    })      
  }

  createTable(data: Igv[]){
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
  openDialog(igv?: Igv){    
    this._dialog.open(IgvDialogComponent, {
      width: '350px',
      data: igv,      
      //TRUE: para NO cerrar con la tacla ESC
      disableClose: false,
    })
  }

}
