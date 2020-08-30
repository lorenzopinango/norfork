import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IInventario, Inventario } from 'app/shared/model/inventario.model';
import { InventarioService } from './inventario.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto/producto.service';

@Component({
  selector: 'jhi-inventario-update',
  templateUrl: './inventario-update.component.html'
})
export class InventarioUpdateComponent implements OnInit {
  isSaving: boolean;

  productos: IProducto[];

  editForm = this.fb.group({
    id: [],
    stock: [null, [Validators.required]],
    productoId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected inventarioService: InventarioService,
    protected productoService: ProductoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ inventario }) => {
      this.updateForm(inventario);
    });
    this.productoService
      .query()
      .subscribe((res: HttpResponse<IProducto[]>) => (this.productos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(inventario: IInventario) {
    this.editForm.patchValue({
      id: inventario.id,
      stock: inventario.stock,
      productoId: inventario.productoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const inventario = this.createFromForm();
    if (inventario.id !== undefined) {
      this.subscribeToSaveResponse(this.inventarioService.update(inventario));
    } else {
      this.subscribeToSaveResponse(this.inventarioService.create(inventario));
    }
  }

  private createFromForm(): IInventario {
    return {
      ...new Inventario(),
      id: this.editForm.get(['id']).value,
      stock: this.editForm.get(['stock']).value,
      productoId: this.editForm.get(['productoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventario>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }
}
