import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Logo from '@/components/Logo'
import Main from '@/components/Main'
import Classify from '@/components/Classify'
import Distribute from '@/components/Distribute'
import Manage from '@/components/Manage'
import Statistics from '@/components/Statistics'

Vue.use(Router);

export default new Router({
  //mode: 'history',
  routes: [
    {
      path: '/',
      redirect: '/login',
      meta: {
        requireAuth: false
      }
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
      meta: {
        requireAuth: false
      }
    },
    {
      path: '/logo',
      name: 'Logo',
      component: Logo,
      meta: {
        requireAuth: false
      }
    },
    {
      path: '/main',
      name: 'Main',
      component: Main,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/classify',
      name: 'Classify',
      component: Classify,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/distribute',
      name: 'Distribute',
      component: Distribute,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/manage',
      name: 'Manage',
      component: Manage,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/statistics',
      name: 'Statistics',
      component: Statistics,
      meta: {
        requireAuth: true
      }
    }
  ]
})
