import { Routes } from "@angular/router"
import { MonedaComponent } from "./moneda/moneda.component"
import { MonedaEditComponent } from "./moneda/moneda-edit/moneda-edit.component"
import { MarcaComponent } from "./marca/marca.component"
import { CategoriaComponent } from "./categoria/categoria.component"
import { TipoDocumentoComponent } from "./tipo-documento/tipo-documento.component"
import { IgvComponent } from "./igv/igv.component"
import { CategoriaDialogComponent } from "./categoria/categoria-dialog/categoria-dialog.component"
import { UnidadMedidaComponent } from "./unidad-medida/unidad-medida.component"
import { ProductoComponent } from "./producto/producto.component"
import { ProductoEditComponent } from "./producto/producto-edit/producto-edit.component"
import { ClienteComponent } from "./cliente/cliente.component"
import { ClienteFormComponent } from "./cliente/cliente-form/cliente-form.component"
import { ProductoAlmacenComponent } from "./producto-almacen/producto-almacen.component"
import { AlmacenComponent } from "./almacen/almacen.component"
import { AlmacenFormComponent } from "./almacen/almacen-form/almacen-form.component"
import { ProductoAlmacenFormComponent } from "./producto-almacen/producto-almacen-form/producto-almacen-form.component"
import { VentaComponent } from "./venta/venta.component"
import { VentaFormComponent } from "./venta/venta-form/venta-form.component"

export const pageRoutes: Routes = [
    {         
        path: 'moneda', component: MonedaComponent, children: 
        [
            { path: 'new', component: MonedaEditComponent },
            { path: 'edit/:id', component: MonedaEditComponent }
        ]
    },
    {path: 'marca', component: MarcaComponent},
    {path: 'categoria', component: CategoriaComponent, children:
        [
            { path: 'new', component: CategoriaDialogComponent },
            { path: 'edit/:id', component: CategoriaDialogComponent }
        ]
    },
    {path: 'tipo-documento', component: TipoDocumentoComponent},
    {path: 'igv', component: IgvComponent},
    {path: 'unidad-medida', component: UnidadMedidaComponent},
    {path: 'producto', component: ProductoComponent, children: 
        [
            { path: 'new', component: ProductoEditComponent},
            { path: 'edit/:id', component: ProductoEditComponent}
        ]
    },
    {path: 'almacen', component: AlmacenComponent, children:
            [
                { path: 'almacenEdit', component: AlmacenFormComponent },
            ]
    },
    {path: 'productoAlmacen', component: ProductoAlmacenComponent, children:
            [
                { path: 'productoAlmacenEdit', component: ProductoAlmacenFormComponent },                
            ]
    },
    {path: 'cliente', component: ClienteComponent, children:
        [            
            { path: 'clienteEdit', component: ClienteFormComponent},
        ]
    },
    {path: 'ventas', component: VentaComponent, children: 
        [
            { path: 'nuevo', component: VentaFormComponent},
            { path: 'editar', component: VentaFormComponent},
        ]
    },

    
]