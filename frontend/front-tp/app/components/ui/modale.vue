<template>
  <section class="fixed top-0 left-0 w-full h-full flex items-center justify-center bg-black/10 backdrop-blur-md">
    <div class="relative w-80 rounded-lg bg-white py-3 px-5 shadow-lg">

      <!-- Header -->
      <div class="flex items-center justify-between border-b border-gray-200 pb-3">
        <h2 class="text-lg font-semibold text-gray-900">
          {{ titre }}
        </h2>

        <!-- Bouton fermer -->
        <button
          @click="emit('fermer')"
          id="pendingModalClose"
          type="button"
          aria-label="Fermer"
          class="group flex h-9 w-9 items-center justify-center rounded-xl transition hover:bg-red-50"
        >
          <span class="relative h-5 w-5">
            <span
              class="absolute left-1/2 top-1/2 h-5 w-0.5 -translate-x-1/2 -translate-y-1/2 rotate-45 rounded-full bg-gray-400 transition group-hover:bg-red-500"
            ></span>

            <span
              class="absolute left-1/2 top-1/2 h-5 w-0.5 -translate-x-1/2 -translate-y-1/2 -rotate-45 rounded-full bg-gray-400 transition group-hover:bg-red-500"
            ></span>
          </span>
        </button>
      </div>

      <!-- Contenu -->
      <div class="pt-4">
        <slot></slot>
      </div>

    </div>
  </section>
</template>


<script setup lang="ts">
defineProps<{
  titre?: string;
}>()

const emit = defineEmits<{
  (e: 'fermer'): void;
}>()
</script>