import { Component, OnInit, ViewChild } from '@angular/core';
import { Cliente } from '../../model/cliente';
import { MatTableDataSource } from '@angular/material/table';
import { ClienteService } from '../../services/cliente.service';
import { MaterialModule } from '../../material/material.module';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router, RouterLink, RouterOutlet } from '@angular/router';
import { switchMap } from 'rxjs';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';


@Component({
  selector: 'app-cliente',
  standalone: true,
  imports: [MaterialModule, RouterLink, RouterOutlet],
  templateUrl: './cliente.component.html',
  styleUrl: './cliente.component.css'
})
export class ClienteComponent implements OnInit{

  dataSource = new MatTableDataSource<Cliente>([]);  
  // displayedColumns: string[] = ['idCliente', 'nombres', 'apellidos', 'tipoDoc','documento', 'acciones'];  
  
  columnDefinitions = [
    { def: 'idCliente', label: 'ID', hide: true},
    { def: 'nombres', label: 'Nombre', hide: false},
    { def: 'apellidos', label: 'Apellido', hide: false},
    { def: 'tipoDoc', label: 'TipoDoc', hide: false},
    { def: 'documento', label: 'Documento', hide: false},
    { def: 'acciones', label: 'Acciones', hide: false},
  ]

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private clienteService: ClienteService,
    private _snackBar: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute //Tambien me ayuda para conocer si tiene activo la ruta hija
  ) {}  

  ngOnInit(): void {  
    this.cargarClientes();
    
    this.clienteService.getClienteChange().subscribe(data =>{
      this.createTable(data);
    })

    this.clienteService.getMessageChange(). subscribe(data => {
      this._snackBar.open(data, 'INFO', {duration: 2000, horizontalPosition: 'right', verticalPosition: 'top'});
      
    })
  }  

  cargarClientes(): void {  
    this.clienteService.findAll().subscribe(clientes => {  
      this.createTable(clientes);
    });  
  }
  
  createTable(data: Cliente[]) {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
  }

  onEdit(element: Cliente) {  
        this.clienteService.selectCliente(element);
        this.router.navigate(['pages/cliente/clienteEdit']);        
  }

  eliminarCliente(id: number): void {  
    this.clienteService.delete(id)
    .pipe(switchMap(() => this.clienteService.findAll()))
    .subscribe(data => {
      this.clienteService.setClienteChange(data);
      this.clienteService.setMessageChange("DELETE!")
    })  
  }

  checkChildren(){    
    return this.route.children.length > 0;
  }

  getDisplayedColumns() {
    return this.columnDefinitions.filter(cd => !cd.hide).map(cd => cd.def);
  }

  applyFilter(e : any){
    this.dataSource.filter = e.target.value.trim();
  }

}
