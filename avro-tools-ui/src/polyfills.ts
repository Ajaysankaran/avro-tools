import * as process from 'process'
import * as buffer from 'buffer'
// import * as crypto from 'crypto'
import * as util from 'util'
import * as stream from 'stream'
// import * as url from 'url'
import * as path from 'path'
import 'assert'
import 'zone.js'

window.process = process
window.Buffer = buffer.Buffer
// window.crypto = crypto
window['util'] = util
window['stream'] = stream
// window.fs = fs
// window.URL = url
window['path'] = path
// window.zlib = zlib