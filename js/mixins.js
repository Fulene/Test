export function mixinDestroy<TBase extends Constructor<{}>>(Base: TBase) {
  return class Destroy extends Base implements OnDestroy {
    protected destroy$: ReplaySubject<boolean> = new ReplaySubject(1);

    constructor(...args: any[]) {
      super(...args);
    }

    ngOnDestroy(): void {
      this.destroy$.next(true);
      this.destroy$.complete();
    }
  };
}

// ==========================================================================

// A revoir => A priori nécessaire pour que les mixines fonctionnent...
export type Constructor<T> = new (...args: any[]) => T;

export type AbstractConstructor<T = object> = abstract new (...args: any[]) => T;

// ==========================================================================

const _Base = mixinDestroy(
  mixinBusy(
    class {
      constructor(public cdr: ChangeDetectorRef) {}
    }
  )
);
@Component({
  selector: 'so-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.scss'],
})
export class UserSettingsComponent extends _Base implements OnInit {
    ...
    ...
    ...
}