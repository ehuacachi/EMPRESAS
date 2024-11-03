import { Routes } from "@angular/router"
import { MonedaComponent } from "./moneda/moneda.component"
import { MonedaEditComponent } from "./moneda/moneda-edit/moneda-edit.component"
import { MarcaComponent } from "./marca/marca.component"
import { CategoriaComponent } from "./categoria/categoria.component"
import { TipoDocumentoComponent } from "./tipo-documento/tipo-documento.component"
import { IgvComponent } from "./igv/igv.component"

export const pageRoutes: Routes = [
    {         
        path: 'moneda', component: MonedaComponent, children: 
        [
            { path: 'new', component: MonedaEditComponent },
            { path: 'edit/:id', component: MonedaEditComponent }
        ]
    },
    {path: 'marca', component: MarcaComponent},
    {path: 'categoria', component: CategoriaComponent},
    {path: 'tipo-documento', component: TipoDocumentoComponent},
    {path: 'igv', component: IgvComponent}

    
]