<template>
  <section class="bg-white rounded-lg shadow-md w-80 mx-20 mt-8">

    <!-- =========================
      EN-TÊTE DE LA TO DO LIST
      ========================= -->
    <div class="bg-purple-500 rounded-t-lg px-4 py-2 flex items-center justify-between">
      <!-- Bouton pour ouvrir le menu -->
      <button @click="toggleModaleMenuNavigation" id="navToggle" class="text-white text-2xl">
        &#9776;
      </button>

      <!-- Titre -->
      <h1 class="text-white text-lg font-semibold">
        Liste de tâches
      </h1>
    </div>


    <!-- =========================
      LISTE DES TÂCHES
      ========================= -->
    <ul class="p-4 max-h-[65vh] overflow-y-scroll">
      <div v-if="taches">
        <li v-for="tache in taches" :key="tache.id"
          class="border-b py-2 bg-violet-100 p-1 rounded flex justify-between items-center mb-2">
          <div>
            <span class="font-semibold">{{ tache.title }}</span>
            <p class="text-gray-600">{{ tache.description }}</p>
            <div class="flex text-center justify-center">
              <label :for="`done-${tache.id}`"
                :class="`cursor-pointer hover:underline ${tache.done ? 'text-green-700' : 'text-blue-700'}`">{{ tache.done
                  ? 'Terminé' : 'À faire'}}</label>
              <input :id="`done-${tache.id}`" type="checkbox" @click.prevent @click="switchTacheTerminee(tache)"
                v-bind:checked="tache.done" class="ml-4 cursor-pointer">
            </div>
          </div>
          <div>
            <div class="flex flex-col items-end  ">
              <button @click="supprimerTache(tache.id)"
                class="bg-red-500 text-white py-1 px-3 rounded hover:bg-red-600 mt-2">
                Supprimer
              </button>
              <button @click="toggleModaleModifierTache(tache)"
                class="bg-blue-500 text-white py-1 px-3 rounded hover:bg-blue-600 mt-2 ml-2">
                Modifier
              </button>
            </div>
          </div>
        </li>
      </div>
    </ul>


    <!-- =========================
      BOUTON : NOUVELLE TÂCHE
      ========================= -->
    <div @click="toggleModaleNouvelleTache" class="flex justify-center">
      <button id="new-task-btn"
        class="bg-purple-500 flex items-center text-white py-2 px-6 rounded-full shadow-md hover:bg-purple-600 mb-4">
        <!-- Icône + -->
        <span class="text-xl">+</span>

        <!-- Texte du bouton -->
        <span class="pl-2">
          Nouvelle tâche
        </span>
      </button>
    </div>


    <!-- =========================
      MODALE : NOUVELLE TÂCHE
      ========================= -->
    <UiModale v-show="modaleNouvelleTache" @fermer="() => { modaleNouvelleTache = false }" titre="Nouvelle tache">
      <form @submit.prevent="ajouterTacheForm()">
        <input v-model="tacheForm.title" placeholder="Titre" id="new-titre" aria-label="Titre de la tâche" />
        <input v-model="tacheForm.description" placeholder="Description" id="new-description"
          aria-label="Description de la tâche" />
        <button type="submit" class="bg-green-600 text-white py-2 px-4 rounded hover:bg-green-700 cursor-pointer ml-1">
          Ajouter
        </button>
        <p v-if="errorForm === 'true'" class="text-red-500 text-sm mt-2">Veuillez remplir tous les champs.</p>
      </form>
    </UiModale>


    <!-- =========================
      MODALE : MODIFIER TÂCHE
      ========================= -->
    <UiModale v-show="modaleModifierTache" @fermer="() => { modaleModifierTache = false }" titre="Modifier tache">
      <form @submit.prevent="modifierTacheForm()">
        <input v-model="tacheFormModifier.title" placeholder="Titre" id="modifier-titre" aria-label="Titre de la tâche" />
        <input v-model="tacheFormModifier.description" placeholder="Description" id="modifier-description" aria-label="Description de la tâche" />
        <button type="submit" class="bg-blue-600 text-white py-2 px-4 rounded hover:bg-blue-700 cursor-pointer ml-1">
          Modifier
        </button>
        <p v-if="errorForm === 'true'" class="text-red-500 text-sm mt-2">Veuillez remplir tous les champs.</p>
      </form>
    </UiModale>

    <!-- =========================
      MODALE : MENU DE NAVIGATION
      ========================= -->
    <UiModale v-show="modalMenuNavigation" @fermer="() => { modalMenuNavigation = false }" titre="Menu de navigation">
      <!-- Afficher les tâches terminées -->
      <button type="button" id="completeBtn"
        class="w-full bg-purple-500 flex items-center justify-center text-white py-2 px-6 rounded-lg shadow-md hover:bg-purple-600 mb-4">
        Terminées
      </button>

      <!-- Afficher les tâches en attente -->
      <button type="button" id="pendingBtn"
        class="w-full bg-purple-500 flex items-center justify-center text-white py-2 px-6 rounded-lg shadow-md hover:bg-purple-600 mb-4">
        En cours
      </button>

      <!-- Fermer le menu -->
      <button type="button" id="navCloseBtn"
        class="w-full bg-purple-500 flex items-center justify-center text-white py-2 px-6 rounded-lg shadow-md hover:bg-purple-600">
        Fermer
      </button>
    </UiModale>


    <!-- =========================
      MODALE : TÂCHES TERMINÉES
      ========================= -->
    <UiModale v-show="modaleTacheTerminee" @fermer="() => { modaleTacheTerminee = false }" titre="Tâches terminées">
      <!-- En-tête de la modale -->
      <div class="flex items-center justify-between">
        <h2 class="text-lg font-semibold">
          Tâches terminées
        </h2>

        <!-- Bouton fermer -->
        <button id="completeModalClose" class="text-gray-500 hover:text-red-500 ml-auto">
          ✕
        </button>
      </div>

      <hr class="my-3">

      <!-- Liste des tâches terminées -->
      <ul id="completed-tasks-list">
      </ul>
    </UiModale>


    <!-- =========================
      MODALE : TÂCHES EN COURS
    ========================= -->
    <UiModale v-show="modaleTacheEnCours" @fermer="() => { modaleTacheEnCours = false }" titre="Tâches en cours">
      <div class="bg-white rounded-lg shadow-lg w-80 p-6">

        <!-- En-tête de la modale -->
        <div class="flex items-center justify-between">
          <h2 class="text-lg font-semibold">
            Tâches en cours
          </h2>
          <hr class="my-3">
          <!-- Liste des tâches en cours -->
          <ul id="pending-tasks-list">
          </ul>
        </div>
      </div>
    </UiModale>
  </section>
</template>


<script setup lang="ts">
// ------ config utilise pour la route de l'API
const config = useRuntimeConfig()

// ------ interface utilisateur pour la liste de tâches
const modaleTacheEnCours = ref(false)
const modaleTacheTerminee = ref(false)
const modaleNouvelleTache = ref(false)
const modalMenuNavigation = ref(false)
const modaleModifierTache = ref(false)

const toggleModaleNouvelleTache = () => {
  errorForm.value = 'false'
  modaleNouvelleTache.value = !modaleNouvelleTache.value
}

const toggleModaleMenuNavigation = () => {
  modalMenuNavigation.value = !modalMenuNavigation.value
}

const toggleModaleModifierTache = (tache: Tache) => {
  errorForm.value = 'false'
  tacheFormModifier.value = { ...tache }
  modaleModifierTache.value = !modaleModifierTache.value
}

// ------ système DATA pour la liste de tâches
import type { Tache, TacheCreateDto } from '@/composables/useTaches'
const { taches, refresh, ajouterTache, supprimerTache, modifierTache, } = useTaches()

const tacheForm = ref<TacheCreateDto>({
  title: '',
  description: '',
  done: false
})

const tacheFormModifier = ref<Tache>({
  id: -1,
  title: 'Erreur',
  description: 'Erreur',
  done: false
})

const errorForm = ref('false')


const ajouterTacheForm = () => {
  if (tacheForm.value.title.trim() === '' || tacheForm.value.description.trim() === '') {
    errorForm.value = 'true'
  } else {
    errorForm.value = 'false'
    ajouterTache(tacheForm.value)
    tacheForm.value = { title: '', description: '', done: false }
    modaleNouvelleTache.value = false
  }
}

const modifierTacheForm = () => {
  if (tacheFormModifier.value.title.trim() === '' || tacheFormModifier.value.description.trim() === '') {
    errorForm.value = 'true'
  } else {
    errorForm.value = 'false'
    modifierTache(tacheFormModifier.value.id, tacheFormModifier.value)
    tacheFormModifier.value = { id: -1, title: 'Erreur', description: 'Erreur', done: false }
    modaleModifierTache.value = false
  }
}

const switchTacheTerminee = (tache: Tache) => {
  tache.done = !tache.done
  modifierTache(tache.id, tache)
}





</script>
