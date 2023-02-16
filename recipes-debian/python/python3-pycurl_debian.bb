#base recipe: meta/recipes-devtools/python/python3-pycurl_7.21.5.bb
#base branch: pyro
#base commit: 69d985c0c3c6576f6d408a0930dfdc61dee3d79d

FILESEXTRAPATHS_prepend := "${THISDIR}/python-pycurl:"

require python-pycurl.inc

inherit distutils3
