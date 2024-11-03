import { Component, ViewChild } from '@angular/core';
import { MaterialModule } from '../../material/material.module';
import { Categoria } from '../../model/categoria';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { CategoriaService } from '../../services/categoria.service';
import { MatDialog } from '@angular/material/dialog';
import { CategoriaDialogComponent } from './categoria-dialog/categoria-dialog.component';

@Component({
  selector: 'app-categoria',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './categoria.component.html',
  styleUrl: './categoria.component.css'
})
export class CategoriaComponent {

  dataSource: MatTableDataSource<Categoria>;
  
  columnDefinitions = [
    { def: 'idCategoria', label: 'ID', hide: true},
    { def: 'descripcion', label: 'Descripcion', hide: false},    
    { def: 'estado', label: 'Estado', hide: false},
    { def: 'acccion', label: 'Acccion', hide: false}
  ]
  //displayedColumns: string[] = [ 'idPatient', 'firstName', 'lastName', 'dni', 'actions'];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private categoriaService: CategoriaService,
    private _dialog: MatDialog    
  ){}

  ngOnInit(): void {
    this.categoriaService.findAll().subscribe(data => {
      this.createTable(data);
    })      
  }

  createTable(data: Categoria[]){
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
  openDialog(categoria?: Categoria){    
    this._dialog.open(CategoriaDialogComponent, {
      width: '350px',
      data: categoria,      
      //TRUE: para NO cerrar con la tacla ESC
      disableClose: false,
    })
  }


}

