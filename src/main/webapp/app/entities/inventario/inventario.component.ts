import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInventario } from 'app/shared/model/inventario.model';
import { InventarioService } from './inventario.service';
import { InventarioDeleteDialogComponent } from './inventario-delete-dialog.component';

@Component({
  selector: 'jhi-inventario',
  templateUrl: './inventario.component.html'
})
export class InventarioComponent implements OnInit, OnDestroy {
  inventarios: IInventario[];
  eventSubscriber: Subscription;

  constructor(protected inventarioService: InventarioService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.inventarioService.query().subscribe((res: HttpResponse<IInventario[]>) => {
      this.inventarios = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInInventarios();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInventario) {
    return item.id;
  }

  registerChangeInInventarios() {
    this.eventSubscriber = this.eventManager.subscribe('inventarioListModification', () => this.loadAll());
  }

  delete(inventario: IInventario) {
    const modalRef = this.modalService.open(InventarioDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.inventario = inventario;
  }
}
