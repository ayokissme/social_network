<template>
  <div>
    <input type="text" placeholder="Write something" v-model="content" />
    <input type="button" value="Save" @click="save" />
  </div>
</template>

<script>
function getIndex(list, id) {
  for (var i = 0; i < list.length; i++ ) {
    if (list[i].id === id) {
      return i
    }
  }
  return -1
}

export default {
  props: ['messages', 'messageAttr'],
  data() {
    return {
      content: '',
      id: ''
    }
  },
  watch: {
    messageAttr(newVal, oldVal) {
      this.content = newVal.content
      this.id = newVal.id
    }
  },
  methods: {
    save() {
      const message = { content: this.content }
      if (this.id) {
        this.$resource('/api/message{/id}').update({id: this.id}, message).then(result =>
            result.json().then(data => {
              const index = getIndex(this.messages, data.id)
              this.messages.splice(index, 1, data)
              this.content = ''
              this.id = ''
            })
        )
      } else {
        this.$resource('/api/message{/id}').save({}, message).then(result =>
            result.json().then(data => {
              this.messages.push(data)
              this.content = ''
            })
        )
      }
    }
  }
}
</script>

<style>
</style>