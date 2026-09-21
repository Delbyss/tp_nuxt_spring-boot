// composables/useTaches.ts
interface Tache {
  id: number
  title: string
  description: string
  done: boolean
}

interface TacheCreateDto {
  title: string
  description: string
  done?: boolean
}


export default function useTaches() {
  const config = useRuntimeConfig()
  const base = import.meta.server ? config.apiBase : config.public.apiBase
  const chemin:string = `${base}tasks/`

  const { data: taches, refresh } = useFetch<Tache[]>(chemin)

  async function ajouterTache(tache: TacheCreateDto) {
    const request = {...tache, completed: tache.done ?? false }

    await $fetch(chemin, {
      method: 'POST',
      body: request,
    })
    await refresh()
  }

  async function supprimerTache(id: number) {
    await $fetch(`${chemin}${id}/`, {
      method: 'DELETE',
    })
    await refresh()
  }

  async function modifierTache(id: number, tache: Partial<TacheCreateDto>) {
    await $fetch(`${chemin}${id}/`, {
      method: 'PATCH',
      body: tache,
    })
    await refresh()
  }

  // pas de lodin et error pour le moment 
  return { taches, ajouterTache, supprimerTache, modifierTache, refresh }
}

export type { Tache, TacheCreateDto }
