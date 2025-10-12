# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-scott-karl;protocol=https;branch=main \
           file://scull-init \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "7fd35aaadcf22afd12e1b16fd634c672ea31df33"

S = "${WORKDIR}/git"

# Inherit module first, then update-rc.d
inherit module update-rc.d

# Flag this package as one which uses init scripts
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "scull-init"
INITSCRIPT_PARAMS = "defaults"

EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"
EXTRA_OEMAKE += "-C ${STAGING_KERNEL_DIR} M=${S}/scull LDDINC=${S}/include"

# Include the init script in the package
FILES:${PN} += "${sysconfdir}/init.d/scull-init"

do_install:append () {
	# Install the init script for scull module
	# ${sysconfdir} typically expands to /etc
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/scull-init ${D}${sysconfdir}/init.d/
}
