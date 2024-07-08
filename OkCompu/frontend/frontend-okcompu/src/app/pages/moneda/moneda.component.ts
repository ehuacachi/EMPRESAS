import { Component, OnInit, ViewChild } from '@angular/core';
import { MaterialModule } from '../../material/material.module';
import { MatTableDataSource } from '@angular/material/table';
import { Moneda } from '../../model/moneda';
import { MonedaService } from '../../services/moneda.service';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { RouterLink, RouterOutlet } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-moneda',
  standalone: true,
  imports: [MaterialModule, RouterOutlet, RouterLink ],
  templateUrl: './moneda.component.html',
  styleUrl: './moneda.component.css'
})
export class MonedaComponent implements OnInit{

  dataSource: MatTableDataSource<Moneda>;
  
  columnDefinitions = [
    { def: 'idMoneda', label: 'idMoneda', hide: true},
    { def: 'descripcion', label: 'descripcion', hide: false},
    { def: 'abreviatura', label: 'abreviatura', hide: false},
    { def: 'estado', label: 'estado', hide: false},
    { def: 'acccion', label: 'acccion', hide: false}
  ]
  //displayedColumns: string[] = [ 'idPatient', 'firstName', 'lastName', 'dni', 'actions'];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private monedaService: MonedaService,
    private _snackBar: MatSnackBar
  ){}

  ngOnInit(): void {
    this.monedaService.findAll().subscribe(data => {
      this.createTable(data);
    });
    
    //Solo se activa cuando existe una actualizacion en la tabla. No se activa cuando el componente se activa por primera vez
    this.monedaService.getMonedaChange().subscribe(data => {
      this.createTable(data);
    });

    this.monedaService.getMessageChange().subscribe(data => {
      this._snackBar.open(data, 'INFO', {duration: 2000, horizontalPosition: 'right', verticalPosition: 'top'})
    })
  }

  getDisplayedColumns(){
    return this.columnDefinitions.filter(cd => !cd.hide).map(cd => cd.def);
  }
  
  createTable(data: Moneda[]){
    this.dataSource = new MatTableDataSource(data);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  applyFilter(e : any){
    this.dataSource.filter = e.target.value.trim();
  }

  delete(idMoneda: number) {
    this.monedaService.delete(idMoneda)
    .pipe(switchMap( () => this.monedaService.findAll() ))
    .subscribe( data => {
      this.monedaService.setMonedaChange(data);
      this.monedaService.setMessageChange('DELETE');
    })
  }

}
