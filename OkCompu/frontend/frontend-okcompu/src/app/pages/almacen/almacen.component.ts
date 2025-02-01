import { Component, OnInit, ViewChild } from '@angular/core';
import { MaterialModule } from '../../material/material.module';
import { Almacen } from '../../model/almacen';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { AlmacenService } from '../../services/almacen.service';
import { ActivatedRoute, Router, RouterLink, RouterOutlet } from '@angular/router';
import { switchMap } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-almacen',
  standalone: true,
  imports: [MaterialModule, RouterOutlet, RouterLink],
  templateUrl: './almacen.component.html',
  styleUrl: './almacen.component.css'
})
export class AlmacenComponent implements OnInit {

  columnDefinitions = [
    { def: 'idAlmacen', label: 'ID', hide: true},
    { def: 'descripcion', label: 'Descripcion', hide: false},
    { def: 'telefonos', label: 'Modelo', hide: false},
    { def: 'domicilios', label: 'Code', hide: false},
    { def: 'accion', label: 'Acccion', hide: false}
  ]

  // dataSource = new MatTableDataSource<Almacen>();  
  dataSource: MatTableDataSource<Almacen>;  

  @ViewChild(MatPaginator) paginator!: MatPaginator;  
  @ViewChild(MatSort) sort!: MatSort;  

  constructor(
    private almacenService: AlmacenService,
    private _snackBar: MatSnackBar,  //para mostrar mensaje 
    private router: Router, //para manejar las rutas de los componentes
    private route: ActivatedRoute //Tambien me ayuda para conocer si tiene activo la ruta hija
  ) {}  

  ngOnInit(): void {  
    this.loadAlmacenes();  
    
    this.almacenService.getAlmacenChange().subscribe( data => {
      this.createTable(data);
    })

    this.almacenService.getMessageChange().subscribe(data => {
      this._snackBar.open(data, 'INFO', {duration: 2000, horizontalPosition: 'right', verticalPosition: 'top'});
    })
  }
  
  loadAlmacenes() {
    this.almacenService.findAll().subscribe(data => {
      this.createTable(data);
    });
  }

  createTable(data: Almacen[]) {
    this.dataSource = new MatTableDataSource(data);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  onEdit(element: Almacen) {  
      this.almacenService.selectAlmacen(element);
      this.router.navigate(['pages/almacen/almacenEdit']);        
  }


  onDelete(id: number) {  
    if (confirm('¿Está seguro de eliminar este almacén?')) {  
      this.almacenService.delete(id)
      .pipe(switchMap(() => this.almacenService.findAll()))
      .subscribe(data => {
        this.almacenService.setAlmacenChange(data);         
        this.almacenService.setMessageChange("Eliminado!");         
      });  
    }  
  }  

  applyFilter(e : any){
    this.dataSource.filter = e.target.value.trim();
  }

  getDisplayedColumns() {
    return this.columnDefinitions.filter(cd => !cd.hide).map(cd => cd.def);
  }

  checkChildren(){    
    return this.route.children.length > 0;
  }

}
